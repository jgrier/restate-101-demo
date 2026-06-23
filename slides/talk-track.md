# Talk track — Restate 101

**Audience:** engineers comfortable with Java, new to durable execution. Goal is an
accurate mental model + a small concrete next step, not a hard sell.
**Format:** ~45–60 min.

**The one thing they should leave with:** *Restate makes the failure-handling that's
normally your code (retries, idempotency, state machines, queues, cron, sagas) the
platform's job — using a journal + deterministic replay, not magic.*

---

## Timing (target ~50 min)

| Min | Segment | Slides |
|----:|---------|--------|
| 0–3 | Open + agenda | Title, Agenda |
| 3–13 | **Part 1 — What can you build** | Reliability tax → Durable execution → What Restate is → What you build → Durable services vs. Temporal |
| 12–30 | **Part 2 — Building blocks** (+ inline demo) | Mental model → 3 blocks → Service/Saga → Virtual Object → Workflow → ctx toolbox |
| 30–44 | **Part 3 — How it works** (+ inline demo) | Journal/replay → the log → partitions → exactly-once → recap |
| 44–48 | Where it fits + try it | Fit, Try it |
| 48–60 | **Q&A / discussion** | — |

If running long, cut the "partitions/leaders" slide to a sentence and keep the
journal/replay slide — that's the irreducible core of Part 3.

---

## Demo choreography

Two options. **Recommended: interleave** — run each block's demo right after its
slide, so concept and proof are adjacent. Fallback: one demo block after Part 3.

**Pre-flight (before the meeting):**
- 3 terminals ready: `restate-server`, `./gradlew run`, a curl terminal.
- Browser open on the **Web UI → http://localhost:9070**. This is your hero visual.
- Do one full dry run end-to-end. Verified: builds clean on SDK 2.8.0.
- Full command list: `demo/README.md`.

**1. Service + Saga** (after the Saga slide)
- Happy path: `add` with `["paid","pro"]` → both activate.
- Saga: `add` with `["paid","blocked"]` → point at the `COMPENSATION:` log lines,
  then open the invocation in the UI and show the **journal** of steps + the undo.
- Line: *"That undo is just a try/catch in Java. No orchestration engine."*

**2. Virtual Object** (after the Virtual Object slide)
- `deposit 10000`, `withdraw 3000`, `balance` → 7000.
- `withdraw 999999` → Insufficient funds (TerminalException, no retry storm).
- Concurrency: the 20-parallel-deposits loop → balance is exactly 2000.
- **Restart `restate-server`**, then `balance` again → still correct. *"State is
  durable and there's no database in this demo."*

**3. Workflow** (after the Workflow slide) — the showstopper
- Start `PaymentApprovalWorkflow/txn-1/run/send`. In the logs, show it **retry the
  flaky notify step twice** then **park** waiting.
- **Restart `restate-server` while it's waiting.** Re-open the UI — the invocation
  is still there, still suspended.
- `decide true` → it wakes and completes. Attach to read the result.
- Line: *"It waited across a full server restart. That 'wait' is a durable promise,
  and it could have been three days."*

**4. (optional) Idempotency** — if time: same deposit twice with one
`idempotency-key` → balance moves once.

---

## Narration notes per part

**Part 1 — get them nodding at the pain.** Spend real time on the *reliability tax*
slide. Ask out loud: "In your payment/onboarding services, what fraction of the code
is retries, idempotency, state tracking, and reconciliation versus actual business
logic?" Let them answer — they'll say "most of it." That's the wedge. Then durable
execution = the platform remembers your steps; checkpoint/save-file analogy.

**Part 2 — it's just Java.** The repeated beat: *plain functions, and anything
durable is a `ctx.` call.* Don't teach all of `ctx` up front — reveal each piece
when its slide/demo comes. Saga = try/catch. Virtual Object = state + one-writer
per key, no locks. Workflow = a parked process that survives anything.

**Part 3 — demystify, don't dazzle.** They're engineers; "magic" loses them.
Journal + replay is the whole idea: a step counts once its result is committed to a
quorum-replicated log; recovery re-runs the handler but feeds back recorded results
until it hits new work. The log is the source of truth; RocksDB is a fast view; S3
snapshots bound replay. Exactly-once = idempotency keys (dedupe client retries) +
epoch fencing (no zombie attempt double-writes). These are primitives they already
trust from Raft/Kafka — frame it that way.

---

## Likely questions (and honest answers)

**"How is this different from Temporal?"** *(there's now a dedicated slide for this
at the end of Part 1 — lead with the "durable services, not just workflows" framing)*
Same durable-execution goal; different shape. Restate ships as one self-contained
binary with its own replicated log and built-in state (no separate Cassandra/DB
cluster, no Elasticsearch). It adds **Virtual Objects** (durable keyed state +
single-writer concurrency) as a first-class primitive, and the latency profile is
low because the log and state are co-located. It's not a workflow-only tool — it's
also stateful services and durable RPC. Don't trash Temporal; position on
operational simplicity + the stateful-object model. *(Defer deep comparisons to a
follow-up.)*

**"What's the failure model / can we lose data?"**
A step is durable only once replicated to a **quorum**; below quorum it isn't
acknowledged. Leaders fail over from the log; epoch fencing prevents split-brain
double-writes. Snapshots go to S3.

**"What's the performance overhead / latency?"**
Each durable step is a quorum log append, so there is a write cost — but state is
local (RocksDB) and reads are fast. It's built in Rust for throughput. Be honest:
durability has a cost; you spend it only on steps you wrap in `ctx`. *(Defer exact
numbers to a benchmark/follow-up.)*

**"Determinism — what breaks replay?"** Side effects not wrapped in `ctx.run`, and
non-deterministic values (time, random, UUIDs) taken outside `ctx` (use
`ctx.random()`/timers). Network/DB calls go in `ctx.run`. The SDK detects journal
mismatches.

**"How do we deploy / version handlers?"** Handlers are normal services (HTTP or
Lambda) you deploy; you register the deployment with Restate. Restate supports
versioned deployments so in-flight invocations finish on their version. *(Light
touch; offer a follow-up deep-dive.)*

**"Language support / does it have to be Java?"** Java, Kotlin, TS/JS, Python, Go,
Rust. Same model. Java is used here because it's a common enterprise stack.

**"Self-hosted or cloud?"** Self-host the single binary on your infra/K8s, or use
Restate's managed cloud. (Confirm the current license terms before quoting them —
don't assert a license name on stage.)

**"Is this production-ready?"** Point to the maturity of the 1.x server line; the
project's founders previously built Apache Flink. Reference customers available on
request.

**"AI agents?"** Restate makes agent loops (LLM call → tool call → repeat) durable
so a long agent run survives restarts and is exactly-once on tool side effects.
Mention as a forward signal; don't rabbit-hole unless they push.

---

## Close — get the next step

Don't end on "thanks." End on a concrete ask:

> "Pick one painful flow — a payment path, an onboarding step, a multi-day approval —
> and prototype it in Restate. One flow, side-by-side with what you run today."

Leave with **a named flow and a named owner** — a small concrete first project beats
a big adoption commitment.
