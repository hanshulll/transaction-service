package com.paymentledger.ledgerconsumer.infrastructure.kafka;

import com.paymentledger.ledgerconsumer.application.RecordLedgerEntryUseCase;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class TransactionEventListener {

	private final RecordLedgerEntryUseCase recordLedgerEntryUseCase;

	public TransactionEventListener(RecordLedgerEntryUseCase recordLedgerEntryUseCase) {
		this.recordLedgerEntryUseCase = recordLedgerEntryUseCase;
	}

	@KafkaListener(topics = "transaction.events", groupId = "ledger-consumer")
	public void onMessage(String rawEvent) {
		throw new UnsupportedOperationException("TODO: deserialize EventEnvelope, delegate to RecordLedgerEntryUseCase");
	}
}
