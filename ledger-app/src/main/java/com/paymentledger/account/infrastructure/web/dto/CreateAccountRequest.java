package com.paymentledger.account.infrastructure.web.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

import java.math.BigDecimal;

public record CreateAccountRequest(
		@NotBlank String ownerName,
		@NotBlank String currency,
		@NotNull @PositiveOrZero BigDecimal initialBalance
) {
}
