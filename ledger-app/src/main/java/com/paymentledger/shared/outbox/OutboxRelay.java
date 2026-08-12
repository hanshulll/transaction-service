package com.paymentledger.shared.outbox;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * Polls outbox_events for unpublished rows and publishes them to transaction.events,
 * keyed by debtorAccountId to preserve per-account ordering. Marks published_at on success.
 */
@Component
public class OutboxRelay {

	private static final int BATCH_SIZE = 100;

	private final OutboxRepository outboxRepository;
	private final KafkaTemplate<String, String> kafkaTemplate;

	public OutboxRelay(OutboxRepository outboxRepository, KafkaTemplate<String, String> kafkaTemplate) {
		this.outboxRepository = outboxRepository;
		this.kafkaTemplate = kafkaTemplate;
	}

//	@Scheduled(fixedDelayString = "${outbox.relay.poll-interval-ms:500}")
	public void pollAndPublish() {
		throw new UnsupportedOperationException("TODO: findUnpublishedBatch, publish each, mark publishedAt");
	}
}
