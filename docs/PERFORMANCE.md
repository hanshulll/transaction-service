# Performance

TODO (Week 6): fill in once the k6 load test has been run.

## Environment

- TODO: hardware specs, single laptop instance, single Postgres, no read replicas.

## Results

| Scenario | TPS | p95 | p99 |
|---|---|---|---|
| Sustained transfer load | TODO | TODO | TODO |
| Duplicate idempotency-key | TODO | TODO | TODO |
| Concurrent same-account | TODO | TODO | TODO |

## Bottleneck diagnosed

TODO: what was found via `EXPLAIN ANALYZE` / connection pool sizing, before/after numbers.

## Stated limits

TODO: what would need to change to go further (read replicas, more brokers, etc).
