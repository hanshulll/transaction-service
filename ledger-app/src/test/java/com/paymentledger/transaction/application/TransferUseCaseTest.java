package com.paymentledger.transaction.application;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class TransferUseCaseTest {

	@Test
	@Disabled("TODO: happy path debits debtor, credits creditor, writes outbox row")
	void transfersHappyPath() {
	}

	@Test
	@Disabled("TODO: insufficient funds fails without touching creditor")
	void rejectsInsufficientFunds() {
	}
}
