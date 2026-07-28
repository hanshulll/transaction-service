package com.paymentledger.config;

import org.springframework.context.annotation.Configuration;

/**
 * Instance-specific Resilience4j customizations, if any are ever needed beyond what
 * application.yml's resilience4j.* properties express. Thresholds for the "fraudCheck"
 * circuit breaker/retry/time-limiter instances live in application.yml - see
 * docs/DECISIONS.md for the reasoning behind the chosen numbers.
 */
@Configuration
public class Resilience4jConfig {
}
