# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## What this repo is

`payment-ledger`: a Maven multi-module reactor simulating a bank-style payment
ledger. Locked architectural decision (see `docs/DECISIONS.md`): a **modular
monolith + exactly one extracted process** — not full microservices. Do not
add a fourth deployable service or split `ledger-app`'s `transaction`/`account`
modules into separate processes; that boundary was deliberately rejected (see
ADR-001/ADR-002).

**Current state:** this is a structural skeleton. Package layout, ports/adapters,
DTOs, entities, Flyway migrations, and Spring config are all wired up and the
reactor builds and tests pass, but nearly every use case / adapter method body
is a `throw new UnsupportedOperationException("TODO: ...")` placeholder. When
asked to "implement" something, look for these TODO stubs rather than assuming
existing code is broken.

## Modules (Maven reactor, groupId `com.paymentledger`)

| Module | Package root | Purpose |
|---|---|---|
| `shared-events` | `com.paymentledger.events` | Event DTOs (`EventEnvelope`, `TransactionCreatedEvent`/`CompletedEvent`/`FailedEvent`), `SchemaVersions`, `CorrelationId`. **Zero business logic** — if you're about to add a decision-making method here, stop, it belongs elsewhere. |
| `ledger-app` | `com.paymentledger.{transaction,account,shared,config}` | The REST API. Owns `payment_ledger` Postgres DB. Runs on port `8080`. |
| `ledger-consumer` | `com.paymentledger.ledgerconsumer` | Kafka consumer that materializes the ledger view. Owns the separate `payment_ledger_view` Postgres DB. Runs on port `8081`. |

Root `pom.xml` is a `packaging=pom` parent (`payment-ledger-parent`) holding
`dependencyManagement` (testcontainers BOM, resilience4j, archunit, shared-events
version). Java 21, Spring Boot 4.0.6.

## Commands

```bash
./mvnw clean install                 # build all 3 modules
./mvnw test                          # run all tests (unit + ArchUnit) across the reactor
./mvnw test -pl ledger-app           # run tests for one module only
./mvnw test -pl ledger-app -Dtest=ModuleBoundaryTest   # run a single test class
./mvnw test -pl ledger-app -Dtest=ModuleBoundaryTest#accountApplicationShouldNotDependOnTransactionInfrastructure  # single test method

make start                           # docker compose up --build -d (full stack: postgres, redis, kafka, both apps, prometheus, grafana)
make stop                            # docker compose down
make logs                            # tail ledger-app + ledger-consumer logs
```

There is no separate lint/format plugin configured — `mvn test`/`mvn install`
is the only gate.

Integration tests use Testcontainers (Postgres, Kafka, Redis) and currently
carry `@Disabled` — Docker must be running once they're implemented and
re-enabled.

## Architecture that spans files

**Ports & adapters per module.** Both `transaction` and `account` inside
`ledger-app` follow the same shape: `domain/` (plain objects, no framework
annotations) → `application/` (use cases + `ports/` interfaces) →
`infrastructure/` (`web/` controllers+DTOs, `persistence/` JPA entities+repos+
adapters implementing the ports, plus module-specific adapters like
`idempotency/`, `fraudcheck/`, `cache/`). A port interface's adapter always
lives in the *same* module's `infrastructure` package, never in another
module's.

**Module boundary rule (non-negotiable, enforced by ArchUnit):**
`transaction.application` must never depend on `account.infrastructure`, and
vice versa — cross-module calls go through the port interfaces
(`AccountPort` in `transaction.application.ports`, implemented by
`account.application.AccountService`), which is how the transaction module
calls into the account module in-process without a network hop. This is
checked by `ledger-app/src/test/java/com/paymentledger/architecture/ModuleBoundaryTest.java`
— run it after any package reshuffling.

**The outbox pattern (`ledger-app/.../shared/outbox/`).** A transfer writes its
`TransactionEntity` row and an `OutboxEventEntity` row in the *same* DB
transaction (via the `OutboxWriter` port → `OutboxWriterAdapter`). A separate
scheduled poller, `OutboxRelay`, reads unpublished rows with
`FOR UPDATE SKIP LOCKED` (see `OutboxRepository.findUnpublishedBatch`) and
publishes them to the `transaction.events` Kafka topic, keyed by
`debtorAccountId` to preserve per-account ordering. This is how the dual-write
problem (DB commit succeeds, Kafka publish fails) is avoided — never make a
use case publish to Kafka directly instead of going through the outbox.

**Idempotent consumption (`ledger-consumer`).** `TransactionEventListener`
must check `ProcessedEventRepository` before writing a `LedgerEntryEntity`, and
insert both the ledger entry and the `processed_events` row in one
transaction, so a redelivered Kafka message is a no-op. Malformed/repeatedly-
failing messages are meant to be routed to `transaction.events.dlt` via
`DeadLetterHandler`, wired into the listener container factory's error
handler — the consumer must never block on a poison message.

**Chaos/resilience wiring.** `ChaosState` (in-memory, mutated by
`POST /admin/chaos`) is read by `FraudCheckStubClient` on every call.
`FraudCheckStubClient` is wrapped with Resilience4j `@CircuitBreaker`/`@Retry`/
`@TimeLimiter` annotations, instance name `fraudCheck`; the actual thresholds
live in each module's `application.yml` under `resilience4j.*`, not in Java
config (`config/Resilience4jConfig.java` is intentionally near-empty — put
instance tuning in YAML and record *why* a threshold was chosen in
`docs/DECISIONS.md`, not in code comments).

**Two logical databases, one Postgres instance.** `init-multi-db.sql` creates
`payment_ledger_view` at container init time (Postgres only creates one DB by
default, named via `POSTGRES_DB=payment_ledger` in `docker-compose.yml`).
`ledger-app` and `ledger-consumer` each own their DB exclusively — never have
one module's Flyway migrations touch the other's schema.

**Two balance read paths.** `GET /balances/{id}/confirmed` reads Postgres
(strongly consistent, slower); `GET /balances/{id}/real-time` reads
`RedisBalanceCache` (cache-aside, sub-ms, eventually consistent). Both must
stay — this is a deliberate, documented consistency/latency tradeoff, not
redundant code.

**Correlation IDs.** `CorrelationIdFilter` puts a correlation id on the MDC
per-request; it flows through `EventEnvelope.correlationId()` into Kafka
messages so a transfer can be traced from HTTP request through outbox through
consumer processing in logs.

## Reference docs in this repo

- `docs/ARCHITECTURE.md` — system diagram and module responsibilities (mostly
  TODO placeholders pending Week 3/4/7 work).
- `docs/DECISIONS.md` — ADR log; every architectural choice should get an
  entry here (decision, alternative rejected, one-sentence why).
- `docs/PERFORMANCE.md` — k6 load test results and diagnosed bottlenecks (TODO,
  filled in during the load-test phase of the build plan).
