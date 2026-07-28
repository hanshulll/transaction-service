package com.paymentledger.shared.chaos;

import org.springframework.stereotype.Component;

import java.util.concurrent.atomic.AtomicLong;

/**
 * Mutable, process-wide toggle read by FraudCheckStubClient on every call. Set via
 * POST /admin/chaos. Intentionally a plain in-memory holder - resets on restart.
 */
@Component
public class ChaosState {

	private final AtomicLong latencyMs = new AtomicLong(0);
	private volatile double failureRate = 0.0;

	public long latencyMs() {
		return latencyMs.get();
	}

	public void setLatencyMs(long latencyMs) {
		this.latencyMs.set(latencyMs);
	}

	public double failureRate() {
		return failureRate;
	}

	public void setFailureRate(double failureRate) {
		this.failureRate = failureRate;
	}
}
