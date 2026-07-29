package com.paymentledger.ledgerconsumer.infrastructure.persistence;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;
import java.util.UUID;

/**
 * Idempotent-consumer dedup table. Check-then-insert in the same transaction as the
 * ledger entry write, so a redelivered Kafka message is a guaranteed no-op.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "processed_events")
public class ProcessedEventEntity {

	@Id
	@Column(name = "event_id")
	private UUID eventId;

	@Column(name = "processed_at", nullable = false)
	private Instant processedAt;
}
