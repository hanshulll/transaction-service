package com.paymentledger.events;

import java.util.UUID;

/**
 * Correlation id shared across a request's lifecycle: HTTP request -> outbox row -> Kafka
 * message -> consumer processing. Carried in {@link EventEnvelope#correlationId()}.
 */
public record CorrelationId(UUID value) {

	public static CorrelationId newId() {
		return new CorrelationId(UUID.randomUUID());
	}

	public static CorrelationId of(String value) {
		return new CorrelationId(UUID.fromString(value));
	}

	@Override
	public String toString() {
		return value.toString();
	}
}
