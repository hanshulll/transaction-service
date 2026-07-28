package com.paymentledger.config;

import org.springframework.boot.micrometer.metrics.autoconfigure.MeterRegistryCustomizer;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ObservabilityConfig {

	@Bean
	public MeterRegistryCustomizer<MeterRegistry> commonTagsCustomizer() {
		return registry -> registry.config().commonTags("application", "ledger-app");
	}
}
