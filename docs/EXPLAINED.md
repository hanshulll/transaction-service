# Payment Ledger — Explained in Plain English

*A companion doc to `docs/ARCHITECTURE.md` and `docs/DECISIONS.md`, written for
understanding the project in plain language before diving into the code.*

---

## 1. What is a "ledger," anyway?

A ledger is just **a record book of money movements**. Think of the old-school
accounting notebook where every entry says *"Account A gave $50 to Account B."*
Banks don't actually move physical cash around when you send money — they just
update numbers in a ledger. Venmo, PayPal, your bank's internal systems — underneath,
they're all ledgers with a nice app on top.

This project is a **simplified simulation of that**: a backend service that lets
you create accounts, hold balances, and transfer money between accounts — with
all the guarantees a real bank needs (nobody's money vanishes, nobody gets
double-charged, the system survives crashes).

---

## 2. What does the project actually do, end to end?

Picture two people, Alice and Bob, both with accounts in the system. Alice wants
to send Bob $150.

1. Alice's app calls `POST /transactions/transfer` with
   `{debtorAccountId: Alice, creditorAccountId: Bob, amount: 150}`.
2. The system checks Alice has enough money, subtracts $150 from her account,
   adds $150 to Bob's account — **as one atomic, all-or-nothing operation**
   (either both happen or neither does).
3. It also runs a quick "is this transfer sketchy?" fraud check (currently a fake
   stub, toggle-able for demos).
4. It records "transfer completed" as an event, so **other parts of the system**
   can find out about it later (e.g. to build a transaction history view) without
   being directly wired into the transfer code.
5. A second, separate mini-program picks up that event and writes it into its
   own "history book" database — this powers a "transaction history" screen
   later, kept separate from live balances for performance/scaling reasons.

That's the whole business idea. Everything else in the blueprint is
**engineering plumbing to make step 2–5 safe, fast, and observable** — which is
exactly the stuff that impresses interviewers, because "move $150 from A to B"
is trivial; doing it *correctly under failure and concurrency* is the actual
hard part of backend engineering.

---

## 3. The two processes, and why there are two (not one, not five)

| | What it is | Analogy |
|---|---|---|
| **ledger-app** | The main app. Handles accounts, transfers, balances. Talks to clients directly (REST API). | The bank teller counter — where you actually deposit/withdraw/transfer |
| **ledger-consumer** | A background worker that only listens for "a transfer happened" events and writes them into a separate reporting database. | The archive room — keeps a historical record, slightly delayed, doesn't block the teller counter |

**Why not just one giant app doing everything?** It would be fine here too —
but splitting the "debit Alice, credit Bob" logic apart gains nothing, since
those two things always change together.

**Why not 5 microservices** (accounts, transfers, notifications, etc.)?
Splitting things that always change together just adds network calls and
failure points for no benefit. The one thing that *does* deserve its own
process is the historical ledger view — it has fundamentally different needs
(fine being a few seconds stale, read-heavy, append-only) — that's a genuine
reason to pay the cost of a separate process.

This "when do you split vs. not" reasoning is exactly the kind of judgment call
SDE2/senior interviews probe for.

---

## 4. The scary-sounding buzzwords, in plain English

**Idempotency key**
If Alice's phone has bad signal and her app retries the transfer request, you
must NOT charge her twice. The client sends a unique "receipt number"
(`Idempotency-Key` header) with the request. The server remembers "I already
processed receipt #123, here's the same answer as before" instead of doing the
transfer again. Stored in Redis (a fast, temporary key-value store) with an
expiry.

**Optimistic locking**
Imagine two transfers hitting Bob's account at the exact same instant from two
different servers. Without protection, both could read "balance = 100" at the
same time, both add $50, and you'd end up with 150 instead of the correct 200.
Optimistic locking adds a version number to each account row; if two updates
race, the database rejects the second one and the code retries. This is how
you prevent "lost updates" under concurrency — a classic interview topic.

**The Outbox Pattern (the fanciest thing in this project)**
Problem: when a transfer completes, you need to (a) save it to the database
AND (b) tell Kafka (the messaging system) about it. What if the database save
succeeds but sending the Kafka message fails (server crashes right in
between)? Now your systems disagree — this is the **dual-write problem**.

Solution: instead of writing to two systems, write to ONE — insert a row into
an `outbox_events` table in the *same* database transaction as the transfer
itself. A separate background job (`OutboxRelay`) constantly polls that table
and publishes rows to Kafka, marking them as sent. If it crashes mid-way, it
just resumes — nothing is lost, nothing sent twice (duplicates are handled
downstream). This guarantees "if the transfer happened, the event will
*eventually* get published" without ever needing two systems to agree
perfectly at the same instant.

**Idempotent consumer (on the receiving end)**
The `ledger-consumer` might receive the same Kafka message twice (message
queues generally guarantee "at least once" delivery, not exactly-once). Before
writing a ledger entry, it checks a `processed_events` table: "have I already
handled event #456?" If yes, skip it. This makes replayed messages harmless.

**Circuit breaker + chaos toggle**
The fraud check is a fake external dependency. In real life, external services
sometimes get slow or fail. A "circuit breaker" watches the failure rate; if
too many calls to the fraud service fail (say, 50% of the last 10 calls), it
"trips" and starts failing fast instead of waiting on a doomed call every
time — protecting the whole system from getting stuck waiting on a broken
dependency. The `/admin/chaos` endpoint is a deliberate on/off switch to
simulate that dependency misbehaving on demand, so you can *prove*, live, that
the safety mechanism works.

**Eventual consistency / two balance endpoints**
`/balances/{id}/confirmed` reads straight from Postgres — always correct, but
slower. `/balances/{id}/real-time` reads from Redis (a cache) — much faster,
but might be a few milliseconds stale after a write. A deliberate, explainable
trade-off between speed and freshness — a core distributed-systems concept.

---

## 5. What sits where

```
shared-events/     -> the "envelope" shape for event messages both apps agree on
ledger-app/
  transaction/     -> transfer logic: validate -> debit -> credit -> record event
  account/         -> account CRUD, balances, the Redis cache
  shared/outbox/    -> the "write once, publish reliably later" mechanism
  shared/chaos/     -> the on/off switch for simulating failures
  shared/error/     -> consistent error responses (RFC 7807 format)
  config/          -> wiring for Kafka, Redis, Resilience4j, metrics
ledger-consumer/   -> the background worker building the historical ledger view
docs/              -> ARCHITECTURE.md (how it's built), DECISIONS.md (why), PERFORMANCE.md (numbers)
docker-compose.yml -> spins up Postgres + Redis + Kafka + both apps + Grafana locally
```

Right now, everything **compiles and the skeleton runs**, but the actual
method bodies (the real transfer logic, real Redis calls, etc.) are
placeholder `throw new UnsupportedOperationException("TODO: ...")` — the
wiring, structure, and contracts are done; the logic gets built module by
module. See `docs/EXPLAINED.md`'s companion doc, `payment-ledger-pending-work.md`
(kept outside the repo), for the full list of what's left.

---

## 6. Leveraging this for SDE2 interviews

This project is deliberately built to give concrete, defensible answers to the
questions SDE2 interviews actually ask, instead of vague "I built a CRUD app"
answers:

1. **"Tell me about a distributed systems problem you solved."**
   → the outbox pattern / dual-write problem. Drawable on a whiteboard in 60
   seconds, and it's a genuinely hard, well-known problem.
2. **"How do you handle duplicate requests / retries?"**
   → idempotency keys, with a test proving concurrent duplicate requests only
   debit once.
3. **"How do you prevent race conditions on shared data?"**
   → optimistic locking + a concurrency test with N threads hitting one
   account.
4. **"How do you make a system resilient to a flaky dependency?"**
   → circuit breaker + retry + the chaos toggle, demoable live.
5. **"How do you decide service boundaries?"**
   → the `docs/DECISIONS.md` ADR on why only the ledger view was extracted, not
   everything.
6. **"How do you monitor/observe a production system?"**
   → Prometheus/Grafana dashboard showing the chaos scenario as a visible
   blip and recovery.
7. **"Tell me about a performance problem you diagnosed."**
   → the Week 6 k6 load test + `EXPLAIN ANALYZE` bottleneck fix with
   before/after numbers.

The strongest move for interviews isn't reciting the tech stack — it's being
able to say, unprompted, *"I chose X over Y because Z,"* for 4–5 decisions in
this project. That's what `docs/DECISIONS.md` is for, and the blueprint itself
calls it out as "worth more in an interview than any individual feature."
