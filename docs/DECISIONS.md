# Decisions (ADR log)

Every entry: the decision, the alternative not picked, and the one-sentence reason why.

## ADR Template

```
## ADR-000: <title>
Date: YYYY-MM-DD

Decision: <what we chose>
Alternative considered: <what we didn't pick>
Why: <one-sentence reason>
```

## ADR-001: Modular monolith + one extracted process, not full microservices

Decision: `ledger-app` hosts the transaction and account modules in one process;
only `ledger-consumer` is a separate deployable.
Alternative considered: three fully independent microservices (transaction,
account, ledger view).
Why: TODO - write this up in your own words (see blueprint Section 1 for the
reasoning to start from).

## ADR-002: Why only ledger-consumer was extracted

TODO (Week 4) - reference the reasoning in Section 1: the ledger view is a
genuinely different consistency domain; the transaction/account modules change
together too often to pay a network-call cost between them.

## ADR-003: Money rounding - HALF_EVEN at scale=4

TODO (Week 1).

## ADR-004: Per-account Kafka partitioning

TODO (Week 3) - `transaction.events` is partitioned by `debtorAccountId` to
preserve per-account ordering.

## ADR-005: Circuit breaker thresholds for the fraud-check stub

TODO (Week 5) - document the chosen failure-rate/window numbers and why.

## ADR-006: Redis idempotency key loss on restart - accepted tradeoff

TODO (Week 2/6).
