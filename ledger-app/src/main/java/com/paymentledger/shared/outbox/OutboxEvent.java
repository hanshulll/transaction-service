package com.paymentledger.shared.outbox;

import java.time.Instant;
import java.util.UUID;

/**
 * Pre-persistence view of an outbox row - what a use case hands to the OutboxWriter port.
 */
public record OutboxEvent(
		String aggregateType,
		UUID aggregateId,
		String eventType,
		Object payload,
		int schemaVersion,
		String kafkaKey,
		Instant createdAt
) {
}
