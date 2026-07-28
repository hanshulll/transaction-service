package com.paymentledger.transaction.application;

import com.paymentledger.transaction.application.ports.AccountPort;
import com.paymentledger.transaction.application.ports.FraudCheckPort;
import com.paymentledger.transaction.application.ports.OutboxWriter;
import com.paymentledger.transaction.application.ports.TransactionRepository;
import com.paymentledger.transaction.domain.Money;
import com.paymentledger.transaction.domain.Transaction;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class TransferUseCase {

	private final TransactionRepository transactionRepository;
	private final AccountPort accountPort;
	private final OutboxWriter outboxWriter;
	private final FraudCheckPort fraudCheckPort;

	public TransferUseCase(TransactionRepository transactionRepository, AccountPort accountPort,
			OutboxWriter outboxWriter, FraudCheckPort fraudCheckPort) {
		this.transactionRepository = transactionRepository;
		this.accountPort = accountPort;
		this.outboxWriter = outboxWriter;
		this.fraudCheckPort = fraudCheckPort;
	}

	public Transaction transfer(String idempotencyKey, UUID debtorAccountId, UUID creditorAccountId, Money amount) {
		throw new UnsupportedOperationException("TODO: validate -> debit -> credit -> write outbox row, single @Transactional method");
	}
}
