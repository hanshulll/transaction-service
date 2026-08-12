# Sprint Status

Read this first. One row per week — update whenever a week starts, finishes,
or carries over. See `README.md` in this folder for the full protocol.

| Week | File | Dates | Status | Carried over from |
|---|---|---|---|---|
| 1 | [week-01.md](week-01.md) | 2026-08-12 → 2026-08-18 | 🟡 In progress | — |

Legend: 🟡 In progress · ✅ Done · ⏸️ Carried over (partial) · ⬜ Not started

## Overall build plan reference

Full 8-week scope lives in `docs/payment-ledger-pending-work.md` (see
"Roughly mapped to the blueprint's week plan"). Weeks in this folder are
generated one at a time from that doc, adjusted for actual pace — not
pre-generated in bulk, since real progress should drive scope, not a fixed
calendar.

Rough target mapping (subject to carry-over/ahead-of-schedule adjustment):

| Week | Theme |
|---|---|
| 1 | Domain model: `Money`, `Transaction`/`TransactionStatus`, `Account` + unit tests |
| 2 | API + idempotency + optimistic locking |
| 3 | Transactional outbox |
| 4 | Extract `ledger-consumer`, idempotent consumption, DLT |
| 5 | Circuit breaker, chaos, compensation |
| 6 | Redis caching + k6 load test |
| 7 | Observability (metrics, Grafana, health indicators) |
| 8 | CI/CD polish, docs, demo, ship |

## How to update this

- Finished the week clean → mark ✅, say "week N done, give me week N+1"
- Ran out of time → mark ⏸️, say "behind on week N, carry forward"
- Blazed through it → say "ahead of schedule, give us more"
