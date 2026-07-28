package com.paymentledger.ledgerconsumer.infrastructure.dlt;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.stereotype.Component;

/**
 * Wired into the Kafka listener container factory's error handler as a
 * DeadLetterPublishingRecoverer target: a message that repeatedly fails to process
 * lands on transaction.events.dlt instead of blocking the consumer.
 */
@Component
public class DeadLetterHandler {

	public void onDeadLetter(ConsumerRecord<String, String> record, Exception exception) {
		throw new UnsupportedOperationException("TODO: log + publish to transaction.events.dlt via DeadLetterPublishingRecoverer");
	}
}
