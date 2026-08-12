# Week 1 — Domain Model & Money Rules

Dates: 2026-08-12 → 2026-08-18
Status: 🟡 In progress
Scope source: `docs/payment-ledger-pending-work.md` → Week 1

## Goal

Make the pure-domain layer (no Spring, no DB, no HTTP) fully real: `Money`,
`TransactionStatus`, `Transaction`, `Account`. Everything else (use cases,
adapters, controllers) builds on this, so nothing here should know about
persistence or the web — that's the whole point of the ports/adapters split
in `CLAUDE.md`.

## Tasks

### 1. `Money` value object
`ledger-app/src/main/java/com/paymentledger/transaction/domain/Money.java`
- [ ] `Money.of(amount, currency)` rejects negative amounts, scales to 4
      decimal places with `RoundingMode.HALF_EVEN`
- [ ] `add()` / `subtract()` throw on currency mismatch
- [ ] Un-`@Disabled` and pass all 3 tests in `MoneyTest.java`
      (`rejectsNegativeAmount`, `throwsOnCurrencyMismatch`,
      `roundsHalfEvenAtScaleFour`)
- [ ] Write **ADR-003** in `docs/DECISIONS.md` (currently a TODO stub) — why
      `HALF_EVEN` at scale 4, not e.g. `HALF_UP` or scale 2

### 2. `TransactionStatus` state machine
`ledger-app/src/main/java/com/paymentledger/transaction/domain/TransactionStatus.java`
- [ ] Implement `canTransitionTo(target)` enforcing exactly: PENDING→DEBITED
      →COMPLETED, and PENDING→DEBITED→FAILED→REVERSED. Everything else is
      illegal.
- [ ] Un-`@Disabled` and pass all 3 tests in `TransactionStatusTest.java`

### 3. `Transaction` aggregate
`ledger-app/src/main/java/com/paymentledger/transaction/domain/Transaction.java`
- [ ] `initiate(idempotencyKey, debtorAccountId, creditorAccountId, amount)`
      builds a PENDING transaction with generated id + timestamps
- [ ] `markDebited` / `markCompleted` / `markFailed` / `markReversed` each
      go through `status.canTransitionTo(...)` and throw
      (`IllegalStateException` or similar) on an illegal transition
- [ ] `markFailed(reason)` stores `failureReason`
- [ ] No test stub exists for this class yet — **create**
      `ledger-app/src/test/java/com/paymentledger/transaction/domain/TransactionTest.java`
      covering: `initiate` sets PENDING, each legal transition, at least one
      illegal transition throws

### 4. `Account` aggregate
`ledger-app/src/main/java/com/paymentledger/account/domain/Account.java`
- [ ] `open(ownerName, currency, initialBalance)` builds an ACTIVE account
- [ ] `debit(amount)` rejects if the resulting balance would go negative, or
      if status != ACTIVE
- [ ] `credit(amount)` rejects if status != ACTIVE
- [ ] No test stub exists for this class yet — **create**
      `ledger-app/src/test/java/com/paymentledger/account/domain/AccountTest.java`
      covering: `open`, debit happy path, debit-would-go-negative rejected,
      credit/debit on a non-ACTIVE account rejected

### 5. Fix now, before it bites later: duplicate `AccountStatus` enums
While wiring up `AccountEntity` you added
`account.infrastructure.persistence.AccountStatus`, but a *different*
`account.domain.AccountStatus` already exists with a different value set:
- `account.domain.AccountStatus`: `ACTIVE, FROZEN, CLOSED`
- `account.infrastructure.persistence.AccountStatus`: `ACTIVE, INACTIVE, SUSPENDED, CLOSED`

- [ ] Decide and fix: either have the JPA entity reuse `account.domain.AccountStatus`
      directly (simplest — nothing outside `account.infrastructure` should
      construct persistence-only enums anyway), or, if you deliberately want
      a wider persistence-level state set, write the explicit mapping in
      `AccountRepositoryAdapter` now so it isn't forgotten once more code
      depends on it.
      **Recommendation:** reuse the domain enum — there's no documented
      reason (no ADR) for persistence to have more states than the domain.

## Stretch (only pull these in if you finish early — tell Claude and they become real tasks)

- [ ] Continue `AccountRepositoryAdapter` domain↔entity mapping (a Week 3
      item, but `AccountEntity` work is already underway)
- [ ] Sketch `TransferUseCase`'s happy-path shape (signature + step
      comments, no logic) so Week 2 starts with less blank-page friction

## Definition of done

- [ ] `./mvnw test -pl ledger-app -Dtest=MoneyTest,TransactionStatusTest,TransactionTest,AccountTest` is green
- [ ] No `UnsupportedOperationException` left in `Money.java`, `Transaction.java`,
      `TransactionStatus.java`, `Account.java`
- [ ] ADR-003 written in `docs/DECISIONS.md`
- [ ] Duplicate `AccountStatus` resolved
- [ ] `docs/sprints/STATUS.md` updated (mark ✅, or ⏸️ + carry-over notes below)

## Carried over from previous week

— none, this is week 1 —
