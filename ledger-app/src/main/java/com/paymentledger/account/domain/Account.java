package com.paymentledger.account.domain;

import com.paymentledger.transaction.domain.Money;

import java.time.Instant;
import java.util.UUID;

public class Account {

	private UUID id;
	private String ownerName;
	private String currency;
	private Money balance;
	private long version;
	private AccountStatus status;
	private Instant createdAt;
	private Instant updatedAt;

	protected Account() {
	}

	public static Account open(String ownerName, String currency, Money initialBalance) {
		throw new UnsupportedOperationException("TODO: build a new ACTIVE account");
	}

	public void debit(Money amount) {
		throw new UnsupportedOperationException("TODO: reject if balance would go negative or account not ACTIVE");
	}

	public void credit(Money amount) {
		throw new UnsupportedOperationException("TODO: reject if account not ACTIVE");
	}

	public UUID id() {
		return id;
	}

	public String ownerName() {
		return ownerName;
	}

	public String currency() {
		return currency;
	}

	public Money balance() {
		return balance;
	}

	public long version() {
		return version;
	}

	public AccountStatus status() {
		return status;
	}

	public Instant createdAt() {
		return createdAt;
	}

	public Instant updatedAt() {
		return updatedAt;
	}
}
