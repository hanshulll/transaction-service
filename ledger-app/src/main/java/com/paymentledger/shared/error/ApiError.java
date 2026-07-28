package com.paymentledger.shared.error;

/**
 * RFC 7807 problem-details shape, used for every error response across the API.
 */
public record ApiError(
		String type,
		String title,
		int status,
		String detail,
		String correlationId
) {
}
