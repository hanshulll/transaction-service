package com.paymentledger.ledgerconsumer.domain;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

public class LedgerEntry {

	private UUID id;
	private UUID transactionId;
	private UUID accountId;
	private EntryType entryType;
	private BigDecimal amount;
	private String currency;
	private BigDecimal balanceAfter;
	private Instant createdAt;

	protected LedgerEntry() {
	}

	public static LedgerEntry of(UUID transactionId, UUID accountId, EntryType entryType, BigDecimal amount, String currency) {
		throw new UnsupportedOperationException("TODO: build a new ledger entry");
	}

	public UUID id() {
		return id;
	}

	public UUID transactionId() {
		return transactionId;
	}

	public UUID accountId() {
		return accountId;
	}

	public EntryType entryType() {
		return entryType;
	}

	public BigDecimal amount() {
		return amount;
	}

	public String currency() {
		return currency;
	}

	public BigDecimal balanceAfter() {
		return balanceAfter;
	}

	public Instant createdAt() {
		return createdAt;
	}
}
