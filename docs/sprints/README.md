# Sprint Tasks — How This Works

This folder tracks the actual coding work as weekly task lists, scoped off
the 8-week plan in `docs/payment-ledger-pending-work.md`. The goal: you never
have to re-explain "where do I start" — you just report status and get the
next batch.

## Files

- **`STATUS.md`** — the single source of truth. One row per week: dates,
  status, carry-overs. Always read/updated first.
- **`week-NN.md`** — one file per week, two-digit zero-padded (`week-01.md`,
  `week-02.md`, ...). Contains that week's checklist, scoped from the pending
  areas in `docs/payment-ledger-pending-work.md`, plus a "definition of done."

Filenames stay `week-NN` (no dates in the name) so a slipped week doesn't
require renaming — actual dates live in `STATUS.md`.

## The three things you can say (no need to re-explain context)

1. **"Week N is done, give me week N+1"**
   Claude verifies the checkboxes against real code state (stubs replaced,
   `@Disabled` tests re-enabled and passing, `./mvnw test` green for the
   touched classes), marks the week ✅ in `STATUS.md`, and generates the next
   `week-NN.md` from the next unclaimed area of the pending-work doc.

2. **"We're behind on week N, carry forward"**
   Claude marks week N ⏸️, rolls the unfinished checkboxes into a "Carried
   over" section at the top of week N+1, and trims that week's *new* scope
   down to compensate so the week stays realistically sized.

3. **"We're ahead, give us more"**
   Claude pulls the next chunk of work from the pending-work doc straight
   into the *current* week file (a "Stretch" section becomes real tasks)
   instead of making you wait for a new week to start.

## Definition of done for any single task

A checkbox is only checked when all of:
- the `UnsupportedOperationException` / TODO stub is replaced with real logic
- the matching test (if one exists in the skeleton) is un-`@Disabled`'d and
  passing; if no test stub exists, a new one was written
- `./mvnw test -pl <module> -Dtest=<Class>` is green for the touched classes

## Ordering

Each week only depends on pieces finished in earlier weeks (this mirrors
`payment-ledger-pending-work.md`'s "Suggested order" section) — domain model
first, then use cases, then adapters, then cross-process/consumer work, then
resilience/caching/observability, then load test + docs polish.
