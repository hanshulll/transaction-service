package com.paymentledger.transaction.infrastructure.persistence;

import com.paymentledger.transaction.application.ports.TransactionRepository;
import com.paymentledger.transaction.domain.Transaction;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Component
public class TransactionRepositoryAdapter implements TransactionRepository {

	private final TransactionJpaRepository jpaRepository;

	public TransactionRepositoryAdapter(TransactionJpaRepository jpaRepository) {
		this.jpaRepository = jpaRepository;
	}

	@Override
	public Transaction save(Transaction transaction) {
		throw new UnsupportedOperationException("TODO: map Transaction <-> TransactionEntity");
	}

	@Override
	public Optional<Transaction> findById(UUID id) {
		throw new UnsupportedOperationException("TODO: map Transaction <-> TransactionEntity");
	}

	@Override
	public Optional<Transaction> findByIdempotencyKey(String idempotencyKey) {
		throw new UnsupportedOperationException("TODO: map Transaction <-> TransactionEntity");
	}
}
