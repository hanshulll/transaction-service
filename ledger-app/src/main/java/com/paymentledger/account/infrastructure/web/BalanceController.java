package com.paymentledger.account.infrastructure.web;

import com.paymentledger.account.application.AccountService;
import com.paymentledger.account.infrastructure.cache.RedisBalanceCache;
import com.paymentledger.account.infrastructure.web.dto.BalanceResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/balances")
public class BalanceController {

	private final AccountService accountService;
	private final RedisBalanceCache redisBalanceCache;

	public BalanceController(AccountService accountService, RedisBalanceCache redisBalanceCache) {
		this.accountService = accountService;
		this.redisBalanceCache = redisBalanceCache;
	}

	@GetMapping("/{id}/real-time")
	public BalanceResponse realTime(@PathVariable UUID id) {
		throw new UnsupportedOperationException("TODO: read from RedisBalanceCache, cache-aside on miss");
	}

	@GetMapping("/{id}/confirmed")
	public BalanceResponse confirmed(@PathVariable UUID id) {
		throw new UnsupportedOperationException("TODO: read strongly-consistent balance from Postgres via AccountService");
	}
}
