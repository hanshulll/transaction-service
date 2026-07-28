package com.paymentledger.ledgerconsumer.infrastructure.persistence;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "ledger_entries")
public class LedgerEntryEntity {

	@Id
	private UUID id;

	@Column(name = "transaction_id", nullable = false)
	private UUID transactionId;

	@Column(name = "account_id", nullable = false)
	private UUID accountId;

	@Column(name = "entry_type", nullable = false)
	private String entryType;

	@Column(nullable = false)
	private BigDecimal amount;

	@Column(nullable = false)
	private String currency;

	@Column(name = "balance_after")
	private BigDecimal balanceAfter;

	@Column(name = "created_at", nullable = false)
	private Instant createdAt;
}
