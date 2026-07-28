package com.paymentledger.transaction.domain;

import java.math.BigDecimal;
import java.util.Currency;

/**
 * Value object wrapping an amount + currency. Never expose a bare BigDecimal across
 * a module boundary - construct/operate through this type instead.
 */
public final class Money {

	private final BigDecimal amount;
	private final Currency currency;

	private Money(BigDecimal amount, Currency currency) {
		this.amount = amount;
		this.currency = currency;
	}

	public static Money of(BigDecimal amount, Currency currency) {
		throw new UnsupportedOperationException("TODO: reject negative amounts, scale to 4 with HALF_EVEN");
	}

	public Money add(Money other) {
		throw new UnsupportedOperationException("TODO: throw on currency mismatch");
	}

	public Money subtract(Money other) {
		throw new UnsupportedOperationException("TODO: throw on currency mismatch");
	}

	public BigDecimal amount() {
		return amount;
	}

	public Currency currency() {
		return currency;
	}
}
