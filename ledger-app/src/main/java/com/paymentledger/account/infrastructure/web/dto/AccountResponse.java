package com.paymentledger.account.infrastructure.web.dto;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

public record AccountResponse(
		UUID id,
		String ownerName,
		String currency,
		BigDecimal balance,
		String status,
		Instant createdAt
) {
}
