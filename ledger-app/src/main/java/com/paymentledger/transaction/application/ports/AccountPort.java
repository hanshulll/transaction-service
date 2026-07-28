package com.paymentledger.transaction.application.ports;

import com.paymentledger.transaction.domain.Money;

import java.util.UUID;

/**
 * In-process port into the account module. Implemented by an adapter in
 * account/infrastructure - never call the account module's internals directly.
 */
public interface AccountPort {

	void debit(UUID accountId, Money amount);

	void credit(UUID accountId, Money amount);
}
