# Architecture

## Overview

Modular monolith (`ledger-app`: transaction + account modules) + one extracted
process (`ledger-consumer`). See the root `README.md` for the system diagram.

TODO:
- [ ] System diagram (copy/refresh the Mermaid diagram from the blueprint)
- [ ] Module boundary rule and why it's enforced by `ModuleBoundaryTest`
- [ ] Sequence diagram: transfer -> outbox -> Kafka -> ledger-consumer (Week 3)
- [ ] Sequence diagram: chaos toggle -> circuit breaker -> compensation (Week 5)

## Modules

| Module | Responsibility |
|---|---|
| `shared-events` | Event DTOs, schema version constants, `CorrelationId` utility. Zero business logic. |
| `ledger-app` | REST API, transaction + account modules, transactional outbox. |
| `ledger-consumer` | Consumes `transaction.events`, materializes the eventually-consistent ledger view. |

## Data flow

TODO: describe the outbox -> Kafka -> consumer flow once Week 3/4 land.
