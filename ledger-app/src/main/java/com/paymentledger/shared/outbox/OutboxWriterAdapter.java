package com.paymentledger.shared.outbox;

import com.paymentledger.transaction.application.ports.OutboxWriter;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class OutboxWriterAdapter implements OutboxWriter {

	private final OutboxRepository outboxRepository;

	public OutboxWriterAdapter(OutboxRepository outboxRepository) {
		this.outboxRepository = outboxRepository;
	}

	@Override
	public void write(String aggregateType, UUID aggregateId, String eventType, Object payload, String kafkaKey) {
		throw new UnsupportedOperationException("TODO: serialize payload, insert OutboxEventEntity in the current transaction");
	}
}
