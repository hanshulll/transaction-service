package com.paymentledger.account.application.ports;

import com.paymentledger.account.domain.Account;

import java.util.Optional;
import java.util.UUID;

public interface AccountRepository {

	Account save(Account account);

	Optional<Account> findById(UUID id);
}
