---
marp: true
theme: default
paginate: true
header: "Restate 101"
style: |
  /* ---- Restate brand: electric blue on light ---- */
  @import url('https://fonts.googleapis.com/css2?family=Inter:wght@400;500;600;700&family=Space+Grotesk:wght@500;600;700&family=JetBrains+Mono:wght@400;500&display=swap');
  :root {
    --bg:#ffffff; --bg2:#f3f6fd; --panel:#ffffff; --line:#d3dae8;
    --ink:#16203a; --muted:#5c6b82; --blue:#1a45ff; --blue-lt:#1447e6;
    --blue-tint:#e5eaff; --navy:#001880;
  }
  section {
    background: var(--bg); color: var(--ink); font-size: 1.6rem;
    font-family: 'Inter', -apple-system, system-ui, sans-serif;
    padding: 60px 64px 64px;
  }
  h1, h2, h3 { font-family: 'Space Grotesk','Inter',sans-serif; color:#0b1020; letter-spacing:-.01em; }
  section h2 { border-bottom: 3px solid var(--blue); padding-bottom:.28em; }
  strong { color:#0b1020; }
  em { color: var(--blue); font-style: normal; font-weight:600; }
  a { color: var(--blue); }
  header { color: var(--muted); font-size:.8rem; letter-spacing:.04em; }
  section::after { color: var(--muted); }            /* page number */
  /* Restate logo on every slide (top-right) */
  section::before {
    content:''; position:absolute; top:26px; right:40px; width:128px; height:40px;
    background:url("data:image/svg+xml;base64,PHN2ZyB4bWxucz0iaHR0cDovL3d3dy53My5vcmcvMjAwMC9zdmciIHdpZHRoPSIxMDEiIGhlaWdodD0iMzIiIGZpbGw9Im5vbmUiIHZpZXdCb3g9IjAgMCAxMDEgMzIiPjxwYXRoIGZpbGw9IiMwQjEwMjAiIGQ9Ik05Ni4wOTIgMjIuMjFxLTEuNTcyIDAtMi43NDctLjY1N2E0Ljg2IDQuODYgMCAwIDEtMS44NDYtMS44ODhxLS42NS0xLjIzLS42NS0yLjg0MnYtLjI1NXEwLTEuNjMzLjY1LTIuODQyLjY1LTEuMjMgMS44MDQtMS44ODcgMS4xNzQtLjY4IDIuNzA2LS42NzkgMS40ODkgMCAyLjYuNjc5YTQuNTUgNC41NSAwIDAgMSAxLjc2MiAxLjg0NXEuNjI5IDEuMTg4LjYyOSAyLjc3OHYuODI4aC03Ljk0OXEuMDQyIDEuMzc4LjkwMiAyLjIwNS44OC44MDYgMi4xODEuODA2IDEuMjE3IDAgMS44MjUtLjU1MWEzLjggMy44IDAgMCAwIC45NjUtMS4yNzNsMS43ODIuOTM0cS0uMjkzLjU5My0uODYgMS4yNS0uNTQ0LjY2LTEuNDQ3IDEuMTA0LS45LjQ0NS0yLjMwNy40NDVtLTMuMDItNi41OTZoNS43MjZxLS4wODQtMS4xODgtLjgzOS0xLjg0NS0uNzU0LS42OC0xLjk3Mi0uNjc5LTEuMjE2IDAtMS45OTIuNjc5LS43NTQuNjU3LS45MjMgMS44NDVNODcuMjU0IDIxLjkxM3EtLjk0NSAwLTEuNTEtLjU3Mi0uNTQ2LS41NzMtLjU0Ni0xLjUyN3YtNi41MTJoLTIuODUydi0xLjg0NWgyLjg1MlY4aDIuMTZ2My40NTdoMy4wODR2MS44NDVoLTMuMDgzdjYuMTNxMCAuNjM2LjU4Ny42MzZoMi4xNnYxLjg0NXpNNzYuNDUzIDIyLjIxcS0xLjExIDAtMS45OTItLjM4MmEzLjMgMy4zIDAgMCAxLTEuMzg1LTEuMTI0cS0uNTAzLS43NDMtLjUwMy0xLjgwMyAwLTEuMDguNTA0LTEuNzgxLjUyNC0uNzIgMS40MDUtMS4wODIuOTAyLS4zNiAyLjAzNC0uMzZoMy4xNDZ2LS42NzlxMC0uOTEyLS41NDUtMS40NjMtLjU0Ni0uNTUyLTEuNjc4LS41NTItMS4xMTEgMC0xLjY5OS41My0uNTg3LjUzLS43NzYgMS4zNzlsLTIuMDEzLS42NThhNC40IDQuNCAwIDAgMSAuNzk3LTEuNTI3cS41NjUtLjY5OSAxLjQ4OS0xLjEyNC45MjItLjQyNCAyLjIyMy0uNDI0IDIuMDEzIDAgMy4xNjcgMS4wNCAxLjE1MyAxLjAzOCAxLjE1MyAyLjk0OHY0LjMwNXEwIC42MzYuNTg4LjYzNmguODh2MS44MjRoLTEuNjE0cS0uNzM0IDAtMS4xOTYtLjM4MS0uNDYyLS4zODItLjQ2MS0xLjA0di0uMDYzaC0uMzE1cS0uMTY4LjMxOS0uNTAzLjc0Mi0uMzM2LjQyNC0uOTg2Ljc0Mi0uNjUuMjk3LTEuNzIuMjk3bS4zMTUtMS44MDNxMS4zIDAgMi4wOTctLjc0Mi43OTctLjc2NC43OTctMi4wNzh2LS4yMTJoLTMuMDJxLS44NiAwLTEuMzg0LjM4MS0uNTI1LjM2LS41MjUgMS4wODIgMCAuNzIuNTQ2IDEuMTQ1LjU0NS40MjQgMS40ODkuNDI0TTY4Ljc5NyAyMS45MTNxLS45NDMgMC0xLjUxLS41NzItLjU0NS0uNTczLS41NDUtMS41Mjd2LTYuNTEySDYzLjg5di0xLjg0NWgyLjg1MlY4aDIuMTZ2My40NTdoMy4wODN2MS44NDVoLTMuMDgzdjYuMTNxMCAuNjM2LjU4OC42MzZoMi4xNnYxLjg0NXpNNTkuNjE5IDIyLjIxcS0xLjk3MiAwLTMuMjcyLS44OS0xLjMtLjg5MS0xLjU5NC0yLjY5NGwyLjAxMy0uNDg4cS4xNjcuODcuNTY2IDEuMzc5LjQuNTEuOTg2LjcyYTMuOSAzLjkgMCAwIDAgMS4zLjIxM3ExLjAyOSAwIDEuNTczLS40MDMuNTY3LS40MDMuNTY3LTEuMDQgMC0uNjM2LS41MjUtLjkzMy0uNTI0LS4yOTctMS41NTItLjQ4N2wtLjcxMy0uMTI4YTkuMiA5LjIgMCAwIDEtMS45MDgtLjU1MXEtLjg2LS4zNi0xLjM2NC0uOTk3LS41MDMtLjYzNi0uNTAzLTEuNjMzIDAtMS40ODUgMS4wOS0yLjI5IDEuMDkyLS44MjggMi44OTUtLjgyOCAxLjc0IDAgMi44NTIuODA2YTMuNTYgMy41NiAwIDAgMSAxLjQ2OCAyLjE0MmwtMi4wMTMuNTczcS0uMTg4LS45NTUtLjc5Ny0xLjMzNi0uNjA4LS40MDMtMS41MS0uNDAzLS44ODEgMC0xLjM4NC4zNGExLjAyIDEuMDIgMCAwIDAtLjUwMy45MTFxMCAuNjM2LjQ4Mi45MzMuNTAzLjI5NyAxLjM0Mi40NDZsLjczNC4xMjdxMS4xMTIuMTkgMi4wMzUuNTMuOTIyLjM0IDEuNDQ3Ljk3Ni41NDUuNjM2LjU0NSAxLjY5NyAwIDEuNTY5LTEuMTU0IDIuNDM5dC0zLjEwNC44N000OS4xMzIgMjIuMjFxLTEuNTcyIDAtMi43NDctLjY1N2E0Ljg2IDQuODYgMCAwIDEtMS44NDYtMS44ODhxLS42NS0xLjIzLS42NS0yLjg0MnYtLjI1NXEwLTEuNjMzLjY1LTIuODQyLjY1LTEuMjMgMS44MDQtMS44ODcgMS4xNzUtLjY4IDIuNzA1LS42NzkgMS40OSAwIDIuNjAxLjY3OWE0LjU1IDQuNTUgMCAwIDEgMS43NjIgMS44NDVxLjYzIDEuMTg4LjYyOSAyLjc3OHYuODI4aC03Ljk0OXEuMDQyIDEuMzc4LjkwMiAyLjIwNS44OC44MDYgMi4xODEuODA2IDEuMjE3IDAgMS44MjUtLjU1MS42MjktLjU1Mi45NjUtMS4yNzNsMS43ODIuOTM0YTYgNiAwIDAgMS0uODYgMS4yNXEtLjU0NS42Ni0xLjQ0NyAxLjEwNC0uOS40NDUtMi4zMDcuNDQ1bS0zLjAyLTYuNTk2aDUuNzI2cS0uMDg0LTEuMTg4LS44NC0xLjg0NS0uNzU0LS42OC0xLjk3LS42NzktMS4yMTggMC0xLjk5My42NzktLjc1NS42NTctLjkyMyAxLjg0NU0zNy45MDkgMjEuOTEzVjExLjQ1N2gyLjExOHYxLjIzaC4zMzVxLjI1Mi0uNjU3Ljc5Ny0uOTU0LjU2Ny0uMzE4IDEuMzg1LS4zMThoMS4yMzd2MS45NzJINDIuNDZxLTEuMDUgMC0xLjcyLjU5NC0uNjcuNTczLS42NzEgMS43ODJ2Ni4xNXoiPjwvcGF0aD48cGF0aCBmaWxsPSIjZmZmIiBkPSJNNiA4aDE5djE1SDZ6Ij48L3BhdGg+PHBhdGggZmlsbD0iIzFBNDVGRiIgZmlsbC1ydWxlPSJldmVub2RkIiBkPSJNMTYgMkM0LjQ3MSAyIDIgNC40NzEgMiAxNnMyLjQ3MSAxNCAxNCAxNCAxNC0yLjQ3MSAxNC0xNFMyNy41MjkgMiAxNiAybS02LjcxIDguOTczYy0uMTg5LjI3OC0uMjkuNjA2LS4yOS45NDJ2OS41MmMwIC4zMjMuMzY1LjUxMS42MjkuMzI0bC45Ni0uNjgyYy4yNjctLjE5LjQyNS0uNDk1LjQyNS0uODIxdi02LjYxOGMwLS4zOTQuNDQ1LS42MjUuNzY3LS4zOThsMy43ODcgMi42NjhhLjMyLjMyIDAgMCAxLS4wMDUuNTI4bC0xLjI3Ljg2YTEuMDAxIDEuMDAxIDAgMSAwIDEuMTMzIDEuNjUybDIuMDc1LTEuNDRhMS42NSAxLjY1IDAgMCAwIC4wMjQtMi42OTVsLTYuMTQ2LTQuNDMxYTEuNDY2IDEuNDY2IDAgMCAwLTEuOTc4LjQyN3ptNi40MDMtLjM3OGExLjAxIDEuMDEgMCAwIDAgLjIzNCAxLjRsNS4xNyAzLjcyM2MuMjY0LjE5LjI3LjU4MS4wMTMuNzhsLTQuMjgzIDMuMzA5YTEuMDA5IDEuMDA5IDAgMCAwIDEuMjMgMS41OTlsNS4xMTItMy45MTZhMS43NDYgMS43NDYgMCAwIDAtLjA0My0yLjgwNWwtNi4wMjMtNC4zMjZhMS4wMDcgMS4wMDcgMCAwIDAtMS40MS4yMzYiIGNsaXAtcnVsZT0iZXZlbm9kZCI+PC9wYXRoPjxwYXRoIGZpbGw9InVybCgjcGFpbnQwX2xpbmVhcl84MzVfMjM1NSkiIGZpbGwtcnVsZT0iZXZlbm9kZCIgZD0iTTE2IDNDNS4yOTUgMyAzIDUuMjk1IDMgMTZzMi4yOTUgMTMgMTMgMTMgMTMtMi4yOTQgMTMtMTNTMjYuNzA2IDMgMTYgMyIgY2xpcC1ydWxlPSJldmVub2RkIj48L3BhdGg+PHBhdGggc3Ryb2tlPSJ1cmwoI3BhaW50MV9saW5lYXJfODM1XzIzNTUpIiBzdHJva2Utb3BhY2l0eT0iMC4zIiBzdHJva2Utd2lkdGg9IjAuNSIgZD0iTTE2IDMuMjVjMi42NyAwIDQuOC4xNDMgNi40OTMuNTIgMS42OTEuMzc1IDIuOTMzLjk4IDMuODQ1IDEuODkyczEuNTE3IDIuMTU0IDEuODkyIDMuODQ1Yy4zNzcgMS42OTQuNTIgMy44MjMuNTIgNi40OTNzLS4xNDMgNC44LS41MiA2LjQ5M2MtLjM3NSAxLjY5MS0uOTggMi45MzMtMS44OTIgMy44NDVzLTIuMTU0IDEuNTE3LTMuODQ1IDEuODkyYy0xLjY5NC4zNzctMy44MjIuNTItNi40OTMuNTItMi42NyAwLTQuOC0uMTQzLTYuNDkzLS41Mi0xLjY5MS0uMzc1LTIuOTMzLS45OC0zLjg0NS0xLjg5MnMtMS41MTctMi4xNTQtMS44OTItMy44NDVDMy4zOTMgMjAuOCAzLjI1IDE4LjY3MSAzLjI1IDE2YzAtMi42Ny4xNDMtNC44LjUyLTYuNDkzLjM3NS0xLjY5MS45OC0yLjkzMyAxLjg5Mi0zLjg0NVM3LjgxNiA0LjE0NSA5LjUwNyAzLjc3QzExLjIgMy4zOTMgMTMuMzMgMy4yNSAxNiAzLjI1WiI+PC9wYXRoPjxwYXRoIHN0cm9rZT0iIzAwMCIgc3Ryb2tlLW9wYWNpdHk9IjAuNSIgc3Ryb2tlLXdpZHRoPSIwLjUiIGQ9Ik0xNiAyLjI1YzIuODc3IDAgNS4xNzEuMTU1IDYuOTk3LjU2IDEuODIzLjQwNiAzLjE2NCAxLjA1OCA0LjE1IDIuMDQ0Ljk4NS45ODUgMS42MzcgMi4zMjYgMi4wNDMgNC4xNDkuNDA1IDEuODI2LjU2IDQuMTIuNTYgNi45OTdzLS4xNTUgNS4xNzEtLjU2IDYuOTk3Yy0uNDA2IDEuODIzLTEuMDU4IDMuMTY0LTIuMDQ0IDQuMTUtLjk4NS45ODUtMi4zMjYgMS42MzctNC4xNDkgMi4wNDMtMS44MjYuNDA1LTQuMTIuNTYtNi45OTcuNTZzLTUuMTcxLS4xNTUtNi45OTctLjU2Yy0xLjgyMy0uNDA2LTMuMTY0LTEuMDU4LTQuMTUtMi4wNDQtLjk4NS0uOTg1LTEuNjM3LTIuMzI2LTIuMDQyLTQuMTQ5LS40MDYtMS44MjYtLjU2MS00LjEyLS41NjEtNi45OTdzLjE1NS01LjE3MS41Ni02Ljk5N2MuNDA2LTEuODIzIDEuMDU4LTMuMTY0IDIuMDQ0LTQuMTUuOTg1LS45ODUgMi4zMjYtMS42MzcgNC4xNDktMi4wNDIgMS44MjYtLjQwNiA0LjEyLS41NjEgNi45OTctLjU2MVoiPjwvcGF0aD48ZGVmcz48bGluZWFyR3JhZGllbnQgaWQ9InBhaW50MF9saW5lYXJfODM1XzIzNTUiIHgxPSIxNiIgeDI9IjE2IiB5MT0iMyIgeTI9IjI5IiBncmFkaWVudFVuaXRzPSJ1c2VyU3BhY2VPblVzZSI+PHN0b3Agc3RvcC1jb2xvcj0iI2ZmZiIgc3RvcC1vcGFjaXR5PSIwLjI1Ij48L3N0b3A+PHN0b3Agb2Zmc2V0PSIxIiBzdG9wLWNvbG9yPSIjZmZmIiBzdG9wLW9wYWNpdHk9IjAiPjwvc3RvcD48L2xpbmVhckdyYWRpZW50PjxsaW5lYXJHcmFkaWVudCBpZD0icGFpbnQxX2xpbmVhcl84MzVfMjM1NSIgeDE9IjE2IiB4Mj0iMTYiIHkxPSIzIiB5Mj0iMjkiIGdyYWRpZW50VW5pdHM9InVzZXJTcGFjZU9uVXNlIj48c3RvcCBzdG9wLWNvbG9yPSIjZmZmIj48L3N0b3A+PHN0b3Agb2Zmc2V0PSIxIiBzdG9wLWNvbG9yPSIjZmZmIiBzdG9wLW9wYWNpdHk9IjAiPjwvc3RvcD48L2xpbmVhckdyYWRpZW50PjwvZGVmcz48L3N2Zz4=") no-repeat center/contain;
    opacity:.95; z-index:5;
  }
  /* section-divider (lead) slides */
  section.lead { background: linear-gradient(135deg,#eaf0ff 0%, #ffffff 60%); border-left:12px solid var(--blue); }
  section.lead h1 { font-size:3.1rem; color:#0b1020; }
  section.lead h3 { color: var(--blue); font-weight:600; }
  /* code: light panel, brand syntax */
  pre { font-size:1.0rem; background: var(--bg2) !important; border:1px solid var(--line); border-radius:12px; }
  pre code { color:#1f2430; font-family:'JetBrains Mono',ui-monospace,monospace; }
  code { font-size:.95em; color: var(--blue-lt); background:#eef2ff; padding:.05em .3em; border-radius:5px;
         font-family:'JetBrains Mono',ui-monospace,monospace; }
  pre code { background:none; padding:0; }
  .hljs-keyword,.hljs-literal,.hljs-built_in { color:#1a45ff; }
  .hljs-string { color:#0a7d55; }
  .hljs-comment { color:#7d8aa0; font-style:italic; }
  .hljs-title,.hljs-title.function_,.hljs-section { color:#6f42c1; }
  .hljs-type,.hljs-class .hljs-title,.hljs-params { color:#0969da; }
  .hljs-number { color:#b3540e; }
  .hljs-meta,.hljs-annotation { color:#c0186a; }     /* @Service, @Handler ... */
  /* tables */
  table { font-size:1.2rem; border-collapse:collapse; }
  th { background: var(--navy); color:#fff; border:1px solid var(--navy); padding:.4em .7em; }
  td { border:1px solid var(--line); color: var(--ink); padding:.4em .7em; }
  tr:nth-child(2n) td { background:#f4f7fc; }
  /* blockquote */
  blockquote { border-left:5px solid var(--blue); color:var(--ink);
               background: rgba(26,69,255,.06); padding:10px 18px; border-radius:0 8px 8px 0; }
  /* helpers */
  .small { font-size:1.1rem; color: var(--muted); }
  .tldr { background: rgba(26,69,255,.08); border-left:6px solid var(--blue); border-radius:8px;
          padding:14px 18px; font-size:1.15em; margin:6px 0 22px; color:#0b1020; }
  .flow { display:flex; align-items:stretch; justify-content:center; gap:0; margin:34px 0 18px; }
  .flow .node { background:var(--panel); border:2.5px solid var(--blue); border-radius:16px;
                padding:20px 24px; min-width:210px; display:flex; flex-direction:column;
                justify-content:center; box-shadow:0 8px 22px rgba(20,40,120,.12); }
  .flow .node.server { box-shadow:0 0 24px rgba(26,69,255,.30); border-color:var(--blue-lt); }
  .flow .node .t { font-weight:700; font-size:1.05em; color:#0b1020; }
  .flow .node .s { color:var(--muted); font-size:.72em; margin-top:10px; line-height:1.6; }
  .flow .arrow { display:flex; flex-direction:column; align-items:center; justify-content:center;
                 color:var(--blue); font-size:1.5em; padding:0 16px; }
  .flow .arrow .back { color:var(--muted); font-size:.9em; }
  .flow-cap { text-align:center; color:var(--muted); font-size:.95em; }
---

<!-- _class: lead -->

# Restate 101

### Build innately resilient distributed applications

<br>

A hands-on 101 with the Restate Java SDK

<!--
SPEAKER: This is a 101. Goal is to give the audience an accurate mental model of
what Restate is, what you build with it, and why it's reliable.
Set the tone: "We'll write some plain Java and watch it survive crashes."
~45–60 min. Three parts: what you can build, the building blocks, how it works.
-->

---

## Agenda

1. **What can you build?** — the problem, and the shift
2. **The building blocks** — Services, Virtual Objects, Workflows
3. **How does it work?** — why it's *super* reliable

<br>

We'll write real Java and break things on purpose.

<!--
SPEAKER: These three map exactly to the agenda the team sent. Promise a live demo
at the end of parts 2 and 3 — the demo is the payoff, keep them waiting for it.
-->

---

<!-- _class: lead -->

# Part 1 — What can you build?

---

## The reliability tax

To make a distributed application *reliable* today, you hand-build:

- **Retries** with backoff — and idempotency keys so retries don't double-charge
- **State machines** to track "where did this get to?"
- **Queues / outbox / DLQs** to not lose work across a crash
- **Schedulers / cron** for "do this in 3 days"
- **Sagas** to undo half-finished work
- **Reconciliation jobs** for when it all drifts anyway

<br>

> This is 80% of the code in a distributed application.
> None of it is the business logic.

<!--
SPEAKER: This slide is where the audience nods. Common pain points in anything that
moves money or coordinates steps: partial failures, "did the transfer go through?",
duplicate charges, multi-day human approvals. Ask: "how much of your service code is
this plumbing vs. actual business logic?" Let them answer.
-->

---

## Durable Execution: the idea

What if the platform remembered **every step your code already did**,
so that after *any* crash it just… kept going?

- You write a plain function.
- Each step is **journaled** as it completes.
- On any failure, the function **resumes from where it was** — completed steps are
  *not* re-run.

<br>

That's **durable execution**. Restate is a durable execution engine.

<!--
SPEAKER: This is the one concept they must leave with. Analogy that lands: a video
game checkpoint / save file. You don't replay the whole level after dying — you
resume at the last checkpoint. Restate checkpoints every step of your code.
-->

---

## What Restate is

A single, self-contained binary that sits **in front of your services** and makes
the plain code you already write durable, exactly-once, and able to hold state.

- Your services stay **stateless to deploy and scale** — Restate manages their state *for* them
- It provides durability, retries, **state**, scheduling, and communication
- **No** Kafka, no separate workflow DB, no Redis, no cron, no Zookeeper to run

<span class="small">Server 1.7.0 · Java SDK 2.8.0 · written in Rust</span>

<!--
SPEAKER: Emphasize "in front of." Restate is a smart durable proxy/broker. Calls
come in through Restate; Restate invokes your handlers and records what happens.
"One binary" matters to a platform team that already runs too much infra.
-->

---

## What you build with it

| You want to… | With Restate |
|---|---|
| Write reliable, durable microservices | **durable handlers** + exactly-once RPC between them |
| Money movement / payments | exactly-once steps + compensation |
| Customer onboarding | **Workflows** that wait days for humans/3rd parties |
| Per-account / per-customer state | **Virtual Objects** — state without a database |
| "Do X in 3 days", reminders, retries | **durable timers** — no cron, no scheduler |
| Event processing | durable handlers off a log/queue |
| **Durable AI agents** | tool calls + memory that survive restarts |

<!--
SPEAKER: Pick the 2-3 rows closest to your audience and dwell there — e.g. for a
bank: payments, onboarding, per-account state. The AI agents row is the current frontier
— Restate is being used to make LLM agent loops durable; mention it lightly, it
signals the platform is forward-looking, but don't get pulled into an AI tangent.
-->

---

## Not just workflows — *durable services*

Workflow engines (e.g. **Temporal**) give you **durable workflows** — a single orchestrator function that drives a sequence of steps.

Restate gives you **durable *services*** — a toolkit for building reliable *systems*:

- reliable invocations from the outside world **and between services** (exactly-once RPC)
- every handler internally runs **durable, multi-step execution** — state, timers, retries, promises
- a workflow is just one flavor; so are stateful objects and plain services

| | Workflow engine (e.g. Temporal) | Restate |
|---|---|---|
| What you build | a workflow | **services**, stateful objects & workflows |
| Service-to-service calls | roll your own | **durable & exactly-once**, built in |
| To operate | server + DB + search index | **one self-contained binary** |

<!--
SPEAKER: This is the differentiation the team will ask about. Lead with the durable-
SERVICES idea: Restate isn't a workflow box off to the side — it's how you build the
whole system of services so their calls to each other are reliable and exactly-once,
and each service internally gets durable execution. Don't trash Temporal; position on
"system of reliable services" + stateful objects + one binary to operate. Defer deep
comparisons to a follow-up.
-->

---

<!-- _class: lead -->

# Part 2 — The building blocks

---

## Request flow — the logical view

Service A handles a request by calling B, then C, and returns a synthesized result.

<div style="text-align:center">
<svg viewBox="0 0 1000 430" width="880" style="max-width:100%;height:auto">
  <defs>
    <marker id="ah1" markerWidth="9" markerHeight="9" refX="7" refY="3" orient="auto"><path d="M0,0 L7,3 L0,6 Z" fill="#1a45ff"/></marker>
    <marker id="ag1" markerWidth="9" markerHeight="9" refX="7" refY="3" orient="auto"><path d="M0,0 L7,3 L0,6 Z" fill="#9aa6bd"/></marker>
  </defs>
  <style>
    .b{fill:#fff;stroke:#1a45ff;stroke-width:2.5}
    .ba{fill:#eef2ff;stroke:#1a45ff;stroke-width:3}
    .t{font-family:'Space Grotesk',Inter,sans-serif;font-weight:700;fill:#0b1020;font-size:22px;text-anchor:middle}
    .s{font-family:Inter,sans-serif;fill:#5c6b82;font-size:14px;text-anchor:middle}
    .nt{fill:#fff;font-family:Inter,sans-serif;font-weight:700;font-size:13px;text-anchor:middle}
  </style>
  <rect class="b" x="50" y="180" width="160" height="72" rx="14"/>
  <text class="t" x="130" y="214">Caller</text><text class="s" x="130" y="236">external</text>
  <rect class="ba" x="410" y="168" width="170" height="96" rx="14"/>
  <text class="t" x="495" y="208">Service A</text><text class="s" x="495" y="230">orchestrates</text>
  <rect class="b" x="800" y="46" width="160" height="72" rx="14"/>
  <text class="t" x="880" y="88">Service B</text>
  <rect class="b" x="800" y="312" width="160" height="72" rx="14"/>
  <text class="t" x="880" y="354">Service C</text>
  <path d="M212,202 L404,202" fill="none" stroke="#1a45ff" stroke-width="2.5" marker-end="url(#ah1)"/>
  <path d="M404,232 L212,232" fill="none" stroke="#9aa6bd" stroke-width="2" stroke-dasharray="6 5" marker-end="url(#ag1)"/>
  <path d="M566,186 L800,108" fill="none" stroke="#1a45ff" stroke-width="2.5" marker-end="url(#ah1)"/>
  <path d="M800,130 L566,208" fill="none" stroke="#9aa6bd" stroke-width="2" stroke-dasharray="6 5" marker-end="url(#ag1)"/>
  <path d="M566,246 L800,324" fill="none" stroke="#1a45ff" stroke-width="2.5" marker-end="url(#ah1)"/>
  <path d="M800,346 L566,268" fill="none" stroke="#9aa6bd" stroke-width="2" stroke-dasharray="6 5" marker-end="url(#ag1)"/>
  <circle cx="300" cy="188" r="13" fill="#1a45ff"/><text class="nt" x="300" y="193">1</text>
  <circle cx="300" cy="246" r="13" fill="#5c6b82"/><text class="nt" x="300" y="251">6</text>
  <circle cx="672" cy="138" r="13" fill="#1a45ff"/><text class="nt" x="672" y="143">2</text>
  <circle cx="700" cy="178" r="13" fill="#5c6b82"/><text class="nt" x="700" y="183">3</text>
  <circle cx="672" cy="292" r="13" fill="#1a45ff"/><text class="nt" x="672" y="297">4</text>
  <circle cx="700" cy="332" r="13" fill="#5c6b82"/><text class="nt" x="700" y="337">5</text>
</svg>
</div>

<p class="flow-cap"><b>1</b> Caller→A · <b>2</b> A→B · <b>3</b> B→A · <b>4</b> A→C · <b>5</b> C→A · <b>6</b> A→Caller (synthesized) &nbsp;—&nbsp; just functions calling functions.</p>

<!--
SPEAKER: This is how you THINK about it and how you WRITE it: plain services calling
each other and returning results. A is an orchestrator that fans out to B and C and
combines the answers. Blue = calls, dashed grey = returns. Next slide: what actually
happens underneath.
-->

---

## Request flow — the physical view

Same logic — but every call is brokered by **Restate**, which journals each hop.

<div style="text-align:center">
<svg viewBox="0 0 1000 430" width="880" style="max-width:100%;height:auto">
  <defs>
    <marker id="ah2" markerWidth="9" markerHeight="9" refX="7" refY="3" orient="auto"><path d="M0,0 L7,3 L0,6 Z" fill="#1a45ff"/></marker>
    <marker id="ag2" markerWidth="9" markerHeight="9" refX="7" refY="3" orient="auto"><path d="M0,0 L7,3 L0,6 Z" fill="#9aa6bd"/></marker>
  </defs>
  <style>
    .b2{fill:#fff;stroke:#1a45ff;stroke-width:2.5}
    .hub{fill:#1a45ff;stroke:#001880;stroke-width:3}
    .th{font-family:'Space Grotesk',Inter,sans-serif;font-weight:700;fill:#fff;font-size:24px;text-anchor:middle}
    .sh{font-family:Inter,sans-serif;fill:#cdd9ff;font-size:14px;text-anchor:middle}
    .t2{font-family:'Space Grotesk',Inter,sans-serif;font-weight:700;fill:#0b1020;font-size:21px;text-anchor:middle}
    .s2{font-family:Inter,sans-serif;fill:#5c6b82;font-size:13px;text-anchor:middle}
  </style>
  <rect class="b2" x="40" y="180" width="150" height="72" rx="14"/>
  <text class="t2" x="115" y="214">Caller</text><text class="s2" x="115" y="234">external</text>
  <rect class="hub" x="378" y="138" width="244" height="172" rx="18"/>
  <text class="th" x="500" y="180">RESTATE</text><text class="sh" x="500" y="202">broker · durable log</text>
  <rect x="430" y="232" width="140" height="13" rx="3" fill="#cdd9ff"/>
  <rect x="430" y="252" width="140" height="13" rx="3" fill="#cdd9ff"/>
  <rect x="430" y="272" width="108" height="13" rx="3" fill="#9fb4ff"/>
  <rect class="b2" x="802" y="44" width="158" height="64" rx="14"/><text class="t2" x="881" y="82">Service A</text>
  <rect class="b2" x="802" y="184" width="158" height="64" rx="14"/><text class="t2" x="881" y="222">Service B</text>
  <rect class="b2" x="802" y="324" width="158" height="64" rx="14"/><text class="t2" x="881" y="362">Service C</text>
  <path d="M190,206 L378,206" fill="none" stroke="#1a45ff" stroke-width="2.5" marker-end="url(#ah2)"/>
  <path d="M378,230 L190,230" fill="none" stroke="#9aa6bd" stroke-width="2" stroke-dasharray="6 5" marker-end="url(#ag2)"/>
  <path d="M610,186 L802,90" fill="none" stroke="#1a45ff" stroke-width="2.5" marker-end="url(#ah2)"/>
  <path d="M802,110 L610,206" fill="none" stroke="#9aa6bd" stroke-width="2" stroke-dasharray="6 5" marker-end="url(#ag2)"/>
  <path d="M624,224 L802,212" fill="none" stroke="#1a45ff" stroke-width="2.5" marker-end="url(#ah2)"/>
  <path d="M802,234 L624,246" fill="none" stroke="#9aa6bd" stroke-width="2" stroke-dasharray="6 5" marker-end="url(#ag2)"/>
  <path d="M610,266 L802,356" fill="none" stroke="#1a45ff" stroke-width="2.5" marker-end="url(#ah2)"/>
  <path d="M802,376 L610,286" fill="none" stroke="#9aa6bd" stroke-width="2" stroke-dasharray="6 5" marker-end="url(#ag2)"/>
</svg>
</div>

<p class="flow-cap">Every hop goes <b>through Restate and into its log before the target runs</b> — that recorded journal is what lets any step resume after a crash.</p>

<!--
SPEAKER: The punchline. The clean A→B→C graph from the last slide is an illusion of
directness — physically every call is brokered by Restate and written to the log
first. Nothing runs until it's durably recorded. That's the whole reliability story
in one picture: there is no "lost" in-flight call, because the call IS a log record.
-->

<!--
NOTE: the per-step ctx.* API (run/get/set/sleep/call) is covered on the "toolbox" slide.
-->

---

## Three building blocks

| | Annotation | Has state? | Concurrency |
|---|---|---|---|
| **Service** | `@Service` | no | parallel |
| **Virtual Object** | `@VirtualObject` | **yes, per key** | **one writer per key** |
| **Workflow** | `@Workflow` | yes, per id | a `run` + signals |

<br>

Same programming model for all three. Pick by what you need.

<!--
SPEAKER: Don't over-explain here — this is the map. We'll see one of each in code.
Service = stateless durable function. Virtual Object = stateful keyed actor.
Workflow = a Virtual Object specialized for one long-running run with signals.
-->

---

## Block 1 — Service (a durable function)

A set of handlers that run as **durable functions** — each step is journaled, retried, and executed exactly once, even across crashes.

```java
@Service
public class SubscriptionService {
  @Handler
  public void add(Context ctx, SubscriptionRequest req) {
    ctx.run("create user", () -> UserClient.create(req.userId(), req.creditCard()));

    for (String plan : req.subscriptions()) {
      ctx.run("subscribe: " + plan,
          () -> PaymentClient.subscribe(req.userId(), req.creditCard(), plan));
    }
  }
}
```

- Each **`ctx.run(...)`** is a durable, **retried**, exactly-once step.
- Crash after "create user"? On recovery it skips it and continues at the loop.

<!--
SPEAKER: This is just a method. The only unusual thing is ctx.run wrapping side
effects. Stress: a plain DB/HTTP call you DON'T wrap would be re-executed on replay
— that's why side effects go in ctx.run. Retries are automatic & exponential.
-->

---

## Sagas — undo half-finished work

```java
List<Runnable> compensations = new ArrayList<>();
try {
  compensations.add(() -> ctx.run("undo: delete user", () -> UserClient.delete(id)));
  ctx.run("create user", () -> UserClient.create(id, card));

  for (String plan : plans) {
    compensations.add(() -> ctx.run("undo: cancel " + plan, () -> cancel(id, plan)));
    ctx.run("subscribe: " + plan, () -> subscribe(id, plan));   // may throw TerminalException
  }
} catch (TerminalException e) {
  for (int i = compensations.size() - 1; i >= 0; i--) compensations.get(i).run();
  throw e;                                  // undo, newest first — durably
}
```

A **`TerminalException`** stops retries. The compensations run — and *they're*
durable too, so even the cleanup survives a crash.

<!--
SPEAKER: Retryable vs terminal is the key distinction. Plain Exception => retried
forever (transient). TerminalException => give up + propagate => your saga undoes.
This is the whole "distributed transaction" story with no 2PC and no orchestration
engine — it's just try/catch in Java. DEMO this one: the "blocked" plan.
-->

---

## Block 2 — Virtual Object (state, no database)

A keyed object that owns **durable state** and serializes writes per key — a per-entity actor with built-in consistency, no database and no locks.

```java
@VirtualObject
public class Account {
  private static final StateKey<Long> BALANCE = StateKey.of("balance", Long.class);

  @Handler   // exclusive: at most ONE runs per account key at a time
  public long withdraw(ObjectContext ctx, long amountCents) {
    long balance = ctx.get(BALANCE).orElse(0L);
    if (amountCents > balance) throw new TerminalException("Insufficient funds");
    ctx.set(BALANCE, balance - amountCents);
    return balance - amountCents;
  }

  @Shared    // read-only, runs concurrently
  public long balance(SharedObjectContext ctx) { return ctx.get(BALANCE).orElse(0L); }
}
```

State lives **in Restate**, keyed by account. **No locks** — Restate serializes
writes per key for you.

<!--
SPEAKER: This is the slide that surprises people. "read-check-write" with NO lock
and NO race because Restate runs one writer per key. The state is durable and
survives restarts. No Postgres, no row locks, no optimistic-concurrency retries.
DEMO: concurrent deposits to one key => exact final balance, every time.
-->

---

## Block 3 — Workflow (waits for the world)

A **durable, long-running process** keyed by id that can wait for events, people, or time — and resumes exactly where it left off after any failure.

```java
@Workflow
public class PaymentApprovalWorkflow {
  static final DurablePromiseKey<Boolean> DECISION = DurablePromiseKey.of("decision", Boolean.class);

  @Workflow
  public String run(WorkflowContext ctx, PaymentRequest req) {
    ctx.run("notify approver", () -> ApprovalClient.requestApproval(ctx.key(), req));
    boolean ok = ctx.promise(DECISION).future().await(Duration.ofDays(3)); // wait up to 3 days
    return ok ? "APPROVED" : "REJECTED";
  }

  @Shared
  public void decide(SharedWorkflowContext ctx, boolean ok) {
    ctx.promiseHandle(DECISION).resolve(ok);     // a human (or system) signals
  }
}
```

The workflow **durably blocks** for days. Restart the server mid-wait — it resumes.

<!--
SPEAKER: A Durable Promise is a "wait" that's persisted. No polling, no DB flag, no
scheduler. The 3-day timer is durable too. This is human-in-the-loop done right:
the process is parked, costs nothing, survives deploys, and wakes on a signal.
DEMO: start it, restart restate-server, then /decide => it completes.
-->

---

## The toolbox on `ctx`

Everything reliability-related is a method on the context:

- `ctx.run(...)` — durable step (retried, exactly-once)
- `ctx.get / set / clear` — durable keyed state
- `ctx.sleep(d)` / durable timers — survive restarts
- `ctx.call / send` — durable RPC (request-response, one-way, **delayed**)
- `ctx.awakeable()` / durable promises — wait for external events
- `TerminalException` — stop retries + drive Sagas

<br>

Plain Java in between. Reliability is a library call, not an architecture.

<!--
SPEAKER: The takeaway line: "reliability is a library call, not an architecture."
You don't redesign around queues and state machines; you call ctx. That's the
pitch to a platform team drowning in plumbing.
-->

---

<!-- _class: lead -->

# Part 3 — How does it work?
### …and why it's *super* reliable

---

## The core technique: journal + replay

For one invocation:

```
ctx.run("create user")   ──▶  result written to the log  ✔ (committed)
ctx.run("subscribe paid")──▶  result written to the log  ✔ (committed)
        💥  CRASH here
─────────────────────────────────────────────────────────
recovery: replay the journal
  • "create user"     → already in log → SKIP (return saved result)
  • "subscribe paid"  → already in log → SKIP (return saved result)
  • next step         → not in log → EXECUTE for real
```

Completed steps are **replayed from the log, not re-executed**.
That's why side effects must go through `ctx`.

<!--
SPEAKER: This is the heart of part 3. Walk it slowly. A "step happened" the moment
its result is durably committed to the log. Recovery = re-run the handler but feed
it the recorded answers for steps already done, until you reach new work. The code
is deterministic between ctx calls, so this is safe.
-->

---

## Where durability actually lives: the log

- Restate has its own **replicated log** (called *Bifrost*).
- A step is "done" the instant its entry is **replicated to a quorum** of nodes.
- The log is the **single source of truth** — everything else is rebuilt from it.

<br>

```
append step ──▶ replicate to quorum ──▶ ACK ──▶ "it happened"
```

No external Kafka/Zookeeper. The replicated log is built in.

<!--
SPEAKER: Quorum commit = a node failure can't lose an acknowledged step. This is
the same durability principle as Raft/replicated logs they already trust. Don't go
deep on Bifrost internals in a 101 — the point is "committed = replicated, durable."
-->

---

## Partitions, leaders, and state

- Work is sharded into **partitions**; each has one **leader** processor (+ followers).
- The leader tails the log, invokes your handlers, and keeps a **materialized cache**
  of state in **RocksDB** for fast reads.
- That cache is **derivative** — it can always be rebuilt from the log. Not a second
  source of truth.
- Periodic **snapshots → S3** bound recovery time (replay only the tail).

<!--
SPEAKER: Mental model: the log is the truth; RocksDB is a fast local view of it;
S3 snapshots mean you don't replay from the beginning of time. This is why a
leader can die and another node takes over with no data loss — it reads the log.
Keep it crisp; this is the deepest slide and it's still 101-level.
-->

---

## Exactly-once, for real

- **Idempotency key** on a request → duplicates return the *same* committed result,
  no re-execution.

<br>

```bash
curl .../Account/acct-42/deposit -H 'idempotency-key: dep-001' --json '500'
curl .../Account/acct-42/deposit -H 'idempotency-key: dep-001' --json '500'
# balance moved by 500, not 1000
```

<!--
SPEAKER: For a bank this is the money slide. An idempotency key means a client can
safely retry — the duplicate returns the same committed result instead of running
again. DEMO the idempotency key live if time allows. (If asked how concurrent
recovery attempts are kept safe, that's epoch fencing — defer the deep detail.)
-->

---

## Why it's *super* reliable — recap

<div class="tldr"><b>TL;DR —</b> Restate handles the hard distributed-systems problems, so your code doesn't have to.</div>

- Every step is saved the moment it finishes — a crash just **resumes**
- **Survives** node and server failures with no data loss
- **No duplicate** side effects, even under retries (exactly-once)
- **Automatic failover** — no manual recovery, no on-call scramble
- **One binary** — far less infrastructure to run (and to break)

<!--
SPEAKER: Land the recap, then go to the live demo (if you saved it) or the wrap.
The one sentence to repeat: the failure-handling that's normally YOUR code is now
the platform's job, and it's done with a log + replay, not magic.
-->

---

<!-- _class: lead -->

# Live demo

`SubscriptionService` · `Account` · `PaymentApprovalWorkflow`

<span class="small">see demo/README.md for the exact commands</span>

<!--
SPEAKER: Run the demo here (or interleave each block earlier). Minimum sequence:
1. Saga: subscribe with ["paid","blocked"] => watch COMPENSATION + the journal in UI.
2. Virtual Object: deposit/withdraw + concurrent deposits => exact balance.
3. Workflow: start it, RESTART restate-server, then /decide => it still completes.
The UI at :9070 showing the per-step journal is the most convincing artifact.
-->

---

## Where it fits

- Payment / transfer flows that must be **exactly-once** and auditable
- Onboarding / servicing that **waits on humans or 3rd parties** for days
- Per-account state without standing up yet another datastore
- Replacing bespoke **retry + queue + cron + saga** plumbing with `ctx.*`
- Durable **AI agent** workflows

<!--
SPEAKER: Tie back to the audience's actual systems by name if you know them. The ask
isn't "adopt everything" — it's "pick one painful flow and prototype it." A small,
concrete first project beats a big commitment.
-->

---

## Try it in 10 minutes

```bash
brew install restatedev/tap/restate-server restatedev/tap/restate
restate-server                                   # terminal 1
./gradlew run                                     # terminal 2  (the demo)
restate deployments register http://localhost:9080
# UI at http://localhost:9070
```

- Docs: **docs.restate.dev** · Examples: **github.com/restatedev/examples**
- This deck + the Java demo: this repo

<br>

### Questions & discussion

<!--
SPEAKER: Hand to Q&A. Offer a small, concrete next step — pick one painful flow and
prototype it — rather than a big adoption ask.
-->
