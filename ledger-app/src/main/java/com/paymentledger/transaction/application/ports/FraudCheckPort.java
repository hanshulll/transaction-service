package com.paymentledger.transaction.application.ports;

import com.paymentledger.transaction.domain.Money;

import java.util.UUID;

public interface FraudCheckPort {

	boolean isApproved(UUID debtorAccountId, UUID creditorAccountId, Money amount);
}
