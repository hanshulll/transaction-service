package com.paymentledger.transaction.domain;

import java.time.Instant;
import java.util.UUID;

public class Transaction {

	private UUID id;
	private String idempotencyKey;
	private UUID debtorAccountId;
	private UUID creditorAccountId;
	private Money amount;
	private TransactionStatus status;
	private String failureReason;
	private Instant createdAt;
	private Instant updatedAt;

	protected Transaction() {
	}

	public static Transaction initiate(String idempotencyKey, UUID debtorAccountId, UUID creditorAccountId, Money amount) {
		throw new UnsupportedOperationException("TODO: build a PENDING transaction");
	}

	public void markDebited() {
		throw new UnsupportedOperationException("TODO: transition via TransactionStatus.canTransitionTo");
	}

	public void markCompleted() {
		throw new UnsupportedOperationException("TODO: transition via TransactionStatus.canTransitionTo");
	}

	public void markFailed(String reason) {
		throw new UnsupportedOperationException("TODO: transition via TransactionStatus.canTransitionTo");
	}

	public void markReversed() {
		throw new UnsupportedOperationException("TODO: transition via TransactionStatus.canTransitionTo");
	}

	public UUID id() {
		return id;
	}

	public String idempotencyKey() {
		return idempotencyKey;
	}

	public UUID debtorAccountId() {
		return debtorAccountId;
	}

	public UUID creditorAccountId() {
		return creditorAccountId;
	}

	public Money amount() {
		return amount;
	}

	public TransactionStatus status() {
		return status;
	}

	public String failureReason() {
		return failureReason;
	}

	public Instant createdAt() {
		return createdAt;
	}

	public Instant updatedAt() {
		return updatedAt;
	}
}
