package com.paymentledger.account.application;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class AccountServiceTest {

	@Test
	@Disabled("TODO: debit/credit under optimistic locking retry, N concurrent writers, exact final balance")
	void concurrentDebitsProduceExactFinalBalance() {
	}
}
