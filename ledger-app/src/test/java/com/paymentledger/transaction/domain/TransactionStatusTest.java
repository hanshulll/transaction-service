package com.paymentledger.transaction.domain;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

class TransactionStatusTest {

	@Test
	@Disabled("TODO: PENDING -> DEBITED -> COMPLETED is a legal path")
	void allowsHappyPathTransitions() {
	}

	@Test
	@Disabled("TODO: PENDING -> DEBITED -> FAILED -> REVERSED is a legal path")
	void allowsFailureAndReversalPath() {
	}

	@Test
	@Disabled("TODO: COMPLETED -> PENDING must throw")
	void rejectsIllegalTransition() {
	}
}
