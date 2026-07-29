package com.paymentledger.account.infrastructure.web;

import com.paymentledger.account.application.AccountService;
import com.paymentledger.account.infrastructure.web.dto.AccountResponse;
import com.paymentledger.account.infrastructure.web.dto.CreateAccountRequest;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/accounts")
public class AccountController {

	private final AccountService accountService;

	public AccountController(AccountService accountService) {
		this.accountService = accountService;
	}

	@PostMapping
	public AccountResponse create(@Valid @RequestBody CreateAccountRequest request) {
		throw new UnsupportedOperationException("TODO: delegate to AccountService.open, map to AccountResponse");
	}

	@GetMapping("/{id}")
	public AccountResponse getById(@PathVariable UUID id) {
		throw new UnsupportedOperationException("TODO: fetch by id, 404 if missing");
	}

	@GetMapping
	public Page<AccountResponse> list(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "20") int size) {
		throw new UnsupportedOperationException("TODO: paginated account listing");
	}
}
