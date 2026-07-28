package com.paymentledger.events;

import java.math.BigDecimal;
import java.util.UUID;

public record TransactionFailedEvent(
		UUID transactionId,
		UUID debtorAccountId,
		UUID creditorAccountId,
		BigDecimal amount,
		String currency,
		String failureReason
) {
}
