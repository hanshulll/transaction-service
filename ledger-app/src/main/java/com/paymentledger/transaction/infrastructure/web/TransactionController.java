package com.paymentledger.transaction.infrastructure.web;

import com.paymentledger.transaction.application.TransferUseCase;
import com.paymentledger.transaction.infrastructure.web.dto.TransferRequest;
import com.paymentledger.transaction.infrastructure.web.dto.TransferResponse;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/transactions")
public class TransactionController {

	private final TransferUseCase transferUseCase;

	public TransactionController(TransferUseCase transferUseCase) {
		this.transferUseCase = transferUseCase;
	}

	@PostMapping("/transfer")
	public TransferResponse transfer(@RequestHeader("Idempotency-Key") String idempotencyKey,
			@Valid @RequestBody TransferRequest request) {
		throw new UnsupportedOperationException("TODO: delegate to TransferUseCase, map Transaction -> TransferResponse");
	}

	@GetMapping("/{id}")
	public TransferResponse getById(@PathVariable UUID id) {
		throw new UnsupportedOperationException("TODO: fetch by id, 404 if missing");
	}
}
