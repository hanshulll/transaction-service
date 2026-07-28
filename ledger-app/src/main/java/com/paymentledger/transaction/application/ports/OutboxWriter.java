package com.paymentledger.transaction.application.ports;

import java.util.UUID;

/**
 * Writes an outbox row in the same DB transaction as the aggregate change it
 * describes. The OutboxRelay is responsible for publishing it to Kafka later.
 */
public interface OutboxWriter {

	void write(String aggregateType, UUID aggregateId, String eventType, Object payload, String kafkaKey);
}
