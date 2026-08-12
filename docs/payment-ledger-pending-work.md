# Payment Ledger — What's Pending

*Companion to `payment-ledger-explained.md` and `payment-ledger-implementation-blueprint_1.md`.
Reflects the state right after the skeleton scaffold: everything compiles and
the reactor builds, but every use case / adapter method body is a
`throw new UnsupportedOperationException("TODO: ...")` placeholder. Nothing
below has been implemented yet.*

---

## By area (22 files with TODO stubs)

| Area | File(s) | What's stubbed, waiting to be built |
|---|---|---|
| Domain rules | `transaction/domain/Money.java` | Construction rejects negative amounts; `add`/`subtract` throw on currency mismatch; `HALF_EVEN` rounding at scale=4 |
| Domain rules | `transaction/domain/Transaction.java`, `transaction/domain/TransactionStatus.java` | `initiate`/`markDebited`/`markCompleted`/`markFailed`/`markReversed`; the `canTransitionTo` state machine (PENDING → DEBITED → COMPLETED / FAILED → REVERSED) |
| Domain rules | `account/domain/Account.java` | `open`, `debit`, `credit` — balance checks, ACTIVE-status checks |
| Transfer flow | `transaction/application/TransferUseCase.java` | The core flow: validate → debit → credit → write outbox row, single `@Transactional` method |
| Transfer flow | `account/application/AccountService.java` | `open`, `getById`, `debit`, `credit` with optimistic-lock retry-with-backoff |
| Persistence adapters | `transaction/.../TransactionRepositoryAdapter.java`, `account/.../AccountRepositoryAdapter.java` | Mapping domain objects ↔ JPA entities |
| API | `transaction/infrastructure/web/TransactionController.java`, `account/infrastructure/web/AccountController.java`, `account/infrastructure/web/BalanceController.java` | Actual request handling — currently every endpoint throws |
| Idempotency | `transaction/infrastructure/idempotency/RedisIdempotencyStore.java` | `tryAcquire` (SETNX+TTL), `getCachedResponse`, `cacheResponse` (replay logic) |
| Outbox | `shared/outbox/OutboxWriterAdapter.java` | Serialize payload, insert `OutboxEventEntity` in the current transaction |
| Outbox | `shared/outbox/OutboxRelay.java` | Poll unpublished batch, publish to Kafka, mark `published_at`; needs a fault-injection test (kill relay mid-loop, assert no lost/duplicated events beyond dedup) |
| Consumer | `ledgerconsumer/infrastructure/kafka/TransactionEventListener.java` | Deserialize `EventEnvelope`, delegate to use case |
| Consumer | `ledgerconsumer/application/RecordLedgerEntryUseCase.java` | Check `processed_events`, insert ledger entry + dedup row in one transaction |
| Consumer | `ledgerconsumer/domain/LedgerEntry.java` | `of(...)` factory |
| Consumer | `ledgerconsumer/infrastructure/dlt/DeadLetterHandler.java` | Publish malformed/poison messages to `transaction.events.dlt` without blocking the consumer |
| Resilience | `transaction/infrastructure/fraudcheck/FraudCheckStubClient.java` | Apply `ChaosState.latencyMs`/`failureRate`, otherwise approve; compensation (reverse debit → `REVERSED`, store reason) lives in `TransferUseCase` once failures are wired |
| Caching | `account/infrastructure/cache/RedisBalanceCache.java` + `resources/lua/increment.lua`, `decrement.lua` | Cache-aside get/set, atomic increment/decrement via the (currently empty) Lua scripts |
| Cross-cutting | `shared/correlation/CorrelationIdFilter.java` | Read/generate correlation id, put on MDC, propagate on response |
| Cross-cutting | `shared/error/ProblemDetailsExceptionHandler.java` | Map validation / domain / unexpected exceptions → RFC 7807 `ApiError` responses |
| Observability | `config/ObservabilityConfig.java` (partially done - common tags bean works) | Transfer latency histogram, TPS counter, cache hit/miss ratio, outbox backlog gauge; Grafana dashboard JSON; custom health indicators (Kafka/Redis connectivity, outbox backlog) |
| Tests | Every `*Test.java` class | All currently `@Disabled` placeholders — no real assertions written. Only `ModuleBoundaryTest` (ArchUnit) is real and passing today |
| Load test | `k6/transfer-load-test.js` | Sustained transfer load + duplicate idempotency-key + concurrent-same-account scenarios |
| Docs | `docs/DECISIONS.md`, `docs/ARCHITECTURE.md`, `docs/PERFORMANCE.md` | ADR entries, sequence diagrams, real benchmark numbers — currently TODO placeholders |
| Ops | `Makefile`'s `demo` target | Scripted demo sequence (transfer → duplicate key → concurrency → chaos toggle) |

---

## Roughly mapped to the blueprint's week plan

Since only the skeleton exists, essentially **all 8 weeks of build content are
still ahead** — nothing below has real logic yet:

- **Week 1 (Foundations & domain model):** `Money`, `Account`/`Transaction`/`TransactionStatus` logic + their unit tests. ArchUnit rule already done.
- **Week 2 (API + idempotency + optimistic locking):** real controller logic, `RedisIdempotencyStore`, optimistic-lock retry, correlation filter, springdoc verification.
- **Week 3 (Transactional outbox):** `OutboxWriterAdapter`, `OutboxRelay`, fault-injection test.
- **Week 4 (Extract ledger-consumer):** `TransactionEventListener`, idempotent consumption, DLT wiring, end-to-end cross-process test.
- **Week 5 (Circuit breaker, chaos, compensation):** `FraudCheckStubClient` real behavior, compensation/reversal logic, breaker-open/recover tests.
- **Week 6 (Redis caching + load test):** `RedisBalanceCache` + Lua scripts, real k6 scenarios, bottleneck diagnosis in `PERFORMANCE.md`.
- **Week 7 (Observability):** metrics instrumentation, Grafana dashboard, custom health indicators.
- **Week 8 (CI/CD, docs, demo, ship):** CI already scaffolded; still need the `demo` script, final README pass, `DECISIONS.md`/`ARCHITECTURE.md` consolidation, demo video.

---

## Suggested order (per your plan to implement this yourself)

1. `Money` + its tests — smallest scope, no Spring dependencies, good for calibrating friction.
2. `TransactionStatus`/`Transaction`/`Account` domain logic + tests.
3. `AccountService` (debit/credit + optimistic locking) and the persistence adapters.
4. `TransferUseCase` happy path, wired through a real controller.
5. `RedisIdempotencyStore`, then the duplicate-request integration test.
6. Outbox write + relay, then the crash-recovery test.
7. `ledger-consumer`'s listener + idempotent-consume + DLT.
8. Chaos/circuit breaker/compensation.
9. Redis balance cache + Lua scripts.
10. Observability, load test, docs/demo — polish pass.

This mirrors the blueprint's own week ordering; each step only depends on
pieces already built in earlier steps.
