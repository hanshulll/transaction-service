package com.paymentledger.ledgerconsumer.application;

import com.paymentledger.ledgerconsumer.infrastructure.persistence.LedgerEntryRepository;
import com.paymentledger.ledgerconsumer.infrastructure.persistence.ProcessedEventRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class RecordLedgerEntryUseCase {

	private final LedgerEntryRepository ledgerEntryRepository;
	private final ProcessedEventRepository processedEventRepository;

	public RecordLedgerEntryUseCase(LedgerEntryRepository ledgerEntryRepository, ProcessedEventRepository processedEventRepository) {
		this.ledgerEntryRepository = ledgerEntryRepository;
		this.processedEventRepository = processedEventRepository;
	}

	public void handle(UUID eventId, Object transactionEvent) {
		throw new UnsupportedOperationException("TODO: check processedEventRepository, insert ledger entry + processed_events row in one transaction");
	}
}
