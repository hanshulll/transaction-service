package com.paymentledger.transaction;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.testcontainers.junit.jupiter.Testcontainers;

/**
 * Full-stack Testcontainers (Postgres, Kafka, Redis) test of the transfer flow.
 * Reuse a singleton container pattern across test classes once implemented.
 */
@Testcontainers
@SpringBootTest
@Disabled("TODO: wire Testcontainers (Postgres, Kafka, Redis) once TransferUseCase is implemented")
class TransferFlowIntegrationTest {

	@Test
	void transferSucceedsEndToEnd() {
	}

	@Test
	void duplicateIdempotencyKeyFiredConcurrentlyResultsInOneDebit() {
	}

	@Test
	void concurrentTransfersOnSameAccountProduceExactFinalBalance() {
	}
}
