package com.paymentledger.transaction.infrastructure.web.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;
import java.util.UUID;

public record TransferRequest(
		@NotNull UUID debtorAccountId,
		@NotNull UUID creditorAccountId,
		@NotNull @Positive BigDecimal amount,
		@NotNull String currency
) {
}
