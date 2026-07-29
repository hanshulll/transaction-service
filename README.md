# payment-ledger

Modular monolith (`ledger-app`) + one extracted process (`ledger-consumer`).
Maven multi-module reactor: `shared-events`, `ledger-app`, `ledger-consumer`.

See `docs/ARCHITECTURE.md` for the system diagram and module boundaries, and
`docs/DECISIONS.md` for the reasoning behind the architecture.

## Repository layout

```
payment-ledger/
├── shared-events/     event DTOs, schema version constants, CorrelationId - zero business logic
├── ledger-app/        REST API - transaction + account modules, transactional outbox
├── ledger-consumer/   consumes transaction.events, materializes the ledger view
├── docs/              ARCHITECTURE.md, DECISIONS.md, PERFORMANCE.md
├── observability/      Prometheus + Grafana provisioning
├── k6/                load test scripts
└── docker-compose.yml
```

## Running locally

```
make start   # docker compose up --build -d
make test    # ./mvnw test
make stop    # docker compose down
```

This is currently a structural skeleton - modules, packages, ports/adapters,
DTOs, Flyway migrations, and config are wired up, but use case and adapter method
bodies are unimplemented (`UnsupportedOperationException` placeholders) pending
the week-by-week build plan.

## Status

Following the 8-week build plan. See `docs/DECISIONS.md` for ADRs as they land.
