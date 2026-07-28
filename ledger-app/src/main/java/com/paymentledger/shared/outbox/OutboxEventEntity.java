package com.paymentledger.shared.outbox;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "outbox_events")
public class OutboxEventEntity {

	@Id
	private UUID id;

	@Column(name = "aggregate_type", nullable = false)
	private String aggregateType;

	@Column(name = "aggregate_id", nullable = false)
	private UUID aggregateId;

	@Column(name = "event_type", nullable = false)
	private String eventType;

	@Column(nullable = false, columnDefinition = "jsonb")
	private String payload;

	@Column(name = "schema_version", nullable = false)
	private int schemaVersion;

	@Column(name = "kafka_key", nullable = false)
	private String kafkaKey;

	@Column(name = "created_at", nullable = false)
	private Instant createdAt;

	@Column(name = "published_at")
	private Instant publishedAt;
}
