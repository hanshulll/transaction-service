package com.paymentledger.account.infrastructure.persistence;

import com.paymentledger.account.application.ports.AccountRepository;
import com.paymentledger.account.domain.Account;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Component
public class AccountRepositoryAdapter implements AccountRepository {

	private final AccountJpaRepository jpaRepository;

	public AccountRepositoryAdapter(AccountJpaRepository jpaRepository) {
		this.jpaRepository = jpaRepository;
	}

	@Override
	public Account save(Account account) {
		throw new UnsupportedOperationException("TODO: map Account <-> AccountEntity");
	}

	@Override
	public Optional<Account> findById(UUID id) {
		throw new UnsupportedOperationException("TODO: map Account <-> AccountEntity");
	}
}
