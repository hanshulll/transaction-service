package com.paymentledger.account.infrastructure.cache;

import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

/**
 * Cache-aside balance cache. Reads/writes go through Lua scripts so increment/decrement
 * is atomic - no read-then-write race under concurrent transfers on the same account.
 */
@Component
public class RedisBalanceCache {

	private static final String KEY_PREFIX = "account:%s:balance";

	private final StringRedisTemplate redisTemplate;
	private final DefaultRedisScript<Long> incrementScript;
	private final DefaultRedisScript<Long> decrementScript;

	public RedisBalanceCache(StringRedisTemplate redisTemplate) {
		this.redisTemplate = redisTemplate;
		this.incrementScript = new DefaultRedisScript<>();
		this.incrementScript.setLocation(new ClassPathResource("lua/increment.lua"));
		this.decrementScript = new DefaultRedisScript<>();
		this.decrementScript.setLocation(new ClassPathResource("lua/decrement.lua"));
	}

	public Optional<BigDecimal> get(UUID accountId) {
		throw new UnsupportedOperationException("TODO: cache-aside read");
	}

	public void set(UUID accountId, BigDecimal balance) {
		throw new UnsupportedOperationException("TODO: write-through on debit/credit");
	}

	public void increment(UUID accountId, BigDecimal amount) {
		throw new UnsupportedOperationException("TODO: EVALSHA incrementScript");
	}

	public void decrement(UUID accountId, BigDecimal amount) {
		throw new UnsupportedOperationException("TODO: EVALSHA decrementScript");
	}
}
