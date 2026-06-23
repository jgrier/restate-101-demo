# Restate 101 — Java demo

Three tiny handlers, one per building block, themed around money movement:

| File | Building block | What it shows |
|------|----------------|---------------|
| `SubscriptionService.java` | **Service** (durable function) | `ctx.run` durable steps, automatic retries, **Saga** compensation |
| `Account.java` | **Virtual Object** | durable keyed **state**, single-writer concurrency (no locks, no DB) |
| `PaymentApprovalWorkflow.java` | **Workflow** | **durable promise**, human-in-the-loop, 3-day durable timer |

Everything is one process (`Main.java`) that the Restate server calls into.

---

## Run it (Docker — recommended)

From the repo root. Needs only Docker — no host `restate-server`, JDK, or Node:

```bash
./scripts/start.sh      # builds + starts Restate, the Java demo, and the UI; registers the service
./scripts/stop.sh       # tear down (add --clean to wipe state)
```

## Run it manually (host binaries, 3 terminals)

If you'd rather not use Docker — needs the Restate CLI/server installed and a JDK 17+:

```bash
brew install restatedev/tap/restate-server restatedev/tap/restate   # one-time

# 1) the Restate server (durability, retries, state, comms live here)
restate-server

# 2) our Java handlers
cd demo && ./gradlew run        # serves the endpoint on :9080

# 3) tell Restate where the handlers are (one time per restart of #2)
restate deployments register http://localhost:9080
```

Open the Web UI at **http://localhost:9070** — you can watch invocations, see the
journal of each step, and inspect Virtual Object state live. This UI is the most
persuasive part of the demo; keep it on screen.

All calls go *through* Restate's ingress on **:8080** (not directly to :9080).

---

## Demo script (the curl cheat sheet)

### 1. Service + retries + Saga

Happy path — note `paid` and `pro` both activate:

```bash
curl localhost:8080/SubscriptionService/add --json '{
  "userId": "alice",
  "creditCard": "4242-4242-4242-1234",
  "subscriptions": ["paid", "pro"]
}'
```

Saga path — `blocked` fails permanently, so the earlier steps are **compensated**
(watch the `COMPENSATION:` log lines and the journal in the UI):

```bash
curl localhost:8080/SubscriptionService/add --json '{
  "userId": "bob",
  "creditCard": "4242-4242-4242-9999",
  "subscriptions": ["paid", "blocked"]
}'
```

### 2. Virtual Object — durable state, single-writer

```bash
curl localhost:8080/Account/acct-42/deposit  --json '10000'   # -> 10000
curl localhost:8080/Account/acct-42/withdraw --json '3000'    # -> 7000
curl localhost:8080/Account/acct-42/balance                   # -> 7000  (no body: handler takes no input)
curl localhost:8080/Account/acct-42/withdraw --json '999999'  # -> Insufficient funds (TerminalException)
```

State survives a server restart — `balance` returns 7000 even after you bounce
`restate-server`. Different keys (`acct-42`, `acct-99`) are fully independent.

To make the "no locks" point, fire many concurrent writes at one key — they
serialize, and the final balance is always exactly right:

```bash
for i in $(seq 1 20); do curl -s localhost:8080/Account/race/deposit --json '100' & done; wait
curl localhost:8080/Account/race/balance                      # -> 2000, every time
```

### 3. Workflow — human-in-the-loop, durable wait

Start the workflow (fire-and-forget). `txn-1` is the workflow id / key. Watch it
retry the flaky "notify approver" step twice, then park, waiting for a decision:

```bash
curl localhost:8080/PaymentApprovalWorkflow/txn-1/run/send --json '{
  "fromAccount": "acct-42",
  "toAccount": "acct-99",
  "amountCents": 5000000,
  "memo": "wire transfer"
}'
```

It is now durably waiting. **Restart `restate-server` here** to prove the wait
survives a crash. Then a human approves (resolves the durable promise):

```bash
curl localhost:8080/PaymentApprovalWorkflow/txn-1/decide --json 'true'
```

Attach to the workflow to read its final result:

```bash
curl localhost:8080/restate/workflow/PaymentApprovalWorkflow/txn-1/attach
# -> "APPROVED: transferred 5000000 cents from acct-42 to acct-99"
```

(If nobody calls `/decide` within 3 days, the workflow auto-rejects — that's the
`await(Duration.ofDays(3))` timer.)

### Bonus: idempotency

Send the same request twice with the same key — it executes **once**:

```bash
curl localhost:8080/Account/acct-42/deposit -H 'idempotency-key: deposit-001' --json '500'
curl localhost:8080/Account/acct-42/deposit -H 'idempotency-key: deposit-001' --json '500'
# balance only moved by 500, not 1000
```

---

## Notes

- Pinned to Restate **server 1.7.x / Java SDK 2.8.0**. Bump `restateVersion` in
  `build.gradle.kts` if newer.
- The `clients/*` classes are fake downstreams; `PaymentClient` rejects the
  `blocked` plan and `ApprovalClient` is deliberately flaky to show retries.
