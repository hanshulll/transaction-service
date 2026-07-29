package com.paymentledger.account.application;

import com.paymentledger.account.application.ports.AccountRepository;
import com.paymentledger.account.domain.Account;
import com.paymentledger.transaction.application.ports.AccountPort;
import com.paymentledger.transaction.domain.Money;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * Account module's application service. Also implements AccountPort so the
 * transaction module can call into this module in-process without depending on
 * account/infrastructure directly.
 */
@Service
public class AccountService implements AccountPort {

	private final AccountRepository accountRepository;

	public AccountService(AccountRepository accountRepository) {
		this.accountRepository = accountRepository;
	}

	public Account open(String ownerName, String currency, Money initialBalance) {
		throw new UnsupportedOperationException("TODO: create and persist a new account");
	}

	public Account getById(UUID id) {
		throw new UnsupportedOperationException("TODO: fetch or throw not-found");
	}

	@Override
	public void debit(UUID accountId, Money amount) {
		throw new UnsupportedOperationException("TODO: load, debit with optimistic locking retry, save");
	}

	@Override
	public void credit(UUID accountId, Money amount) {
		throw new UnsupportedOperationException("TODO: load, credit with optimistic locking retry, save");
	}
}
