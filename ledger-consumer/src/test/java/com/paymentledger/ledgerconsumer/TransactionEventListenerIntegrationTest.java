package com.paymentledger.ledgerconsumer;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.testcontainers.junit.jupiter.Testcontainers;

@Testcontainers
@SpringBootTest
@Disabled("TODO: wire Testcontainers (Postgres, Kafka) once TransactionEventListener is implemented")
class TransactionEventListenerIntegrationTest {

	@Test
	void replayingSameEventThreeTimesProducesOneLedgerEntry() {
	}

	@Test
	void malformedMessageLandsOnDeadLetterTopicWithoutBlockingConsumer() {
	}
}
