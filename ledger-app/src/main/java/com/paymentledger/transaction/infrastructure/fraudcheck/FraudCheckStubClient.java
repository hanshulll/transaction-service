package com.paymentledger.transaction.infrastructure.fraudcheck;

import com.paymentledger.shared.chaos.ChaosState;
import com.paymentledger.transaction.application.ports.FraudCheckPort;
import com.paymentledger.transaction.domain.Money;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import io.github.resilience4j.timelimiter.annotation.TimeLimiter;
import org.springframework.stereotype.Component;

import java.util.UUID;

/**
 * Synchronous stand-in for a downstream fraud-check dependency. Reads {@link ChaosState}
 * on every call so latency/failure behavior can be toggled live via /admin/chaos.
 */
@Component
public class FraudCheckStubClient implements FraudCheckPort {

	private final ChaosState chaosState;

	public FraudCheckStubClient(ChaosState chaosState) {
		this.chaosState = chaosState;
	}

	@Override
	@CircuitBreaker(name = "fraudCheck")
	@TimeLimiter(name = "fraudCheck")
	@Retry(name = "fraudCheck")
	public boolean isApproved(UUID debtorAccountId, UUID creditorAccountId, Money amount) {
		throw new UnsupportedOperationException("TODO: apply chaosState.latencyMs / failureRate, otherwise approve");
	}
}
