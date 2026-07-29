package com.paymentledger.transaction.infrastructure.idempotency;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.Optional;

/**
 * SETNX + TTL backed idempotency store keyed by the client-supplied Idempotency-Key
 * header. A replayed key within the TTL window returns the cached response instead
 * of re-processing the transfer.
 */
@Component
public class RedisIdempotencyStore {

	private static final Duration DEFAULT_TTL = Duration.ofHours(24);

	private final StringRedisTemplate redisTemplate;

	public RedisIdempotencyStore(StringRedisTemplate redisTemplate) {
		this.redisTemplate = redisTemplate;
	}

	public boolean tryAcquire(String idempotencyKey) {
		throw new UnsupportedOperationException("TODO: SETNX idempotencyKey with DEFAULT_TTL");
	}

	public Optional<String> getCachedResponse(String idempotencyKey) {
		throw new UnsupportedOperationException("TODO: read cached response JSON, if present");
	}

	public void cacheResponse(String idempotencyKey, String responseJson) {
		throw new UnsupportedOperationException("TODO: store response JSON with DEFAULT_TTL");
	}
}
