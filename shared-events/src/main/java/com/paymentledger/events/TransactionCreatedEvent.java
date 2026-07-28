package com.paymentledger.events;

import java.math.BigDecimal;
import java.util.UUID;

public record TransactionCreatedEvent(
		UUID transactionId,
		UUID debtorAccountId,
		UUID creditorAccountId,
		BigDecimal amount,
		String currency
) {
}
