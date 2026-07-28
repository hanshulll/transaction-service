package com.paymentledger.events;

import java.time.Instant;
import java.util.UUID;

/**
 * Wraps every event published to {@code transaction.events}.
 * schemaVersion allows consumers to evolve independently of producers.
 */
public record EventEnvelope<T>(
		UUID eventId,
		int schemaVersion,
		String eventType,
		Instant occurredAt,
		UUID correlationId,
		T payload
) {
}
