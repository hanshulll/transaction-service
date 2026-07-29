package com.paymentledger.transaction.domain;

/**
 * Valid path: PENDING -> DEBITED -> COMPLETED
 * Failure path: PENDING -> DEBITED -> FAILED -> REVERSED
 */
public enum TransactionStatus {
	PENDING,
	DEBITED,
	COMPLETED,
	FAILED,
	REVERSED;

	public boolean canTransitionTo(TransactionStatus target) {
		throw new UnsupportedOperationException("TODO: enforce the valid-transition state machine");
	}
}
