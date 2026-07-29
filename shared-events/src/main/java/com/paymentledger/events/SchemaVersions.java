package com.paymentledger.events;

/**
 * Current schema versions for each event type published on {@code transaction.events}.
 * Bump the relevant constant when a payload shape changes.
 */
public final class SchemaVersions {

	public static final int TRANSACTION_CREATED_V1 = 1;
	public static final int TRANSACTION_COMPLETED_V1 = 1;
	public static final int TRANSACTION_FAILED_V1 = 1;

	private SchemaVersions() {
	}
}
