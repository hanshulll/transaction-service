package com.paymentledger.shared.chaos;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin/chaos")
public class ChaosController {

	public record ChaosRequest(long latencyMs, double failureRate) {
	}

	private final ChaosState chaosState;

	public ChaosController(ChaosState chaosState) {
		this.chaosState = chaosState;
	}

	@PostMapping
	public void configure(@RequestBody ChaosRequest request) {
		chaosState.setLatencyMs(request.latencyMs());
		chaosState.setFailureRate(request.failureRate());
	}
}
