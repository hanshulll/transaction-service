package com.paymentledger.transaction.infrastructure.web.dto;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

public record TransferResponse(
		UUID transactionId,
		UUID debtorAccountId,
		UUID creditorAccountId,
		BigDecimal amount,
		String currency,
		String status,
		Instant createdAt
) {
}
