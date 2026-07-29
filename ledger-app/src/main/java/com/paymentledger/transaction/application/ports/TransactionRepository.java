package com.paymentledger.transaction.application.ports;

import com.paymentledger.transaction.domain.Transaction;

import java.util.Optional;
import java.util.UUID;

public interface TransactionRepository {

	Transaction save(Transaction transaction);

	Optional<Transaction> findById(UUID id);

	Optional<Transaction> findByIdempotencyKey(String idempotencyKey);
}
