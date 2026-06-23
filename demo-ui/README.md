# Restate 101 — demo UI

A clickable browser UI for the demo, so you can drive it live without typing curl.
One panel per building block, plus a request console that shows every call and its
HTTP response (so the audience sees exactly what's happening), and a link to the
Restate journal UI.

```
┌─ Service · Saga ─┐ ┌─ Virtual Object ─┐ ┌─ Workflow ───────┐
│ Subscription     │ │ Account          │ │ Payment approval │
│ (tick "blocked"  │ │ deposit/withdraw │ │ start / approve  │
│  to roll back)   │ │ balance · ⚡race │ │ reject / result  │
└──────────────────┘ └──────────────────┘ └──────────────────┘
                 REQUEST CONSOLE (live request/response log)
```

## Run it

**Easiest:** `./scripts/start.sh` from the repo root runs this UI in Docker along
with Restate and the demo — nothing to start by hand.

**Standalone** (Restate + Java demo already running on the standard ports — see
`../demo/README.md`):

```bash
cd demo-ui
node server.mjs            # -> http://localhost:8088
```

Open **http://localhost:8088**.

- Zero dependencies — just Node (built-in `http` module).
- It serves the page on `:8088` and proxies every `/api/*` request to the Restate
  ingress at `:8080`. The proxy is only there so the browser calls the same origin
  it loaded from — no CORS setup needed.
- Point at a different ingress with `RESTATE_INGRESS=http://host:port node server.mjs`.

## Suggested live flow

1. **Saga** — add a subscription with `blocked` ticked → watch the rollback message,
   then open the journal UI to show the compensations.
2. **Virtual Object** — deposit/withdraw, then hit **⚡ 20 concurrent deposits** →
   balance moves by exactly 2000. No locks.
3. **Workflow** — Start payment, **restart `restate-server`**, then Approve →
   Get result. It waited across the crash.

The request console mirrors what you'd otherwise show with curl — keep it visible.
