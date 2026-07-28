package com.paymentledger.shared.outbox;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;
import jakarta.persistence.LockModeType;

public interface OutboxRepository extends JpaRepository<OutboxEventEntity, UUID> {

	/**
	 * FOR UPDATE SKIP LOCKED so a multi-threaded relay never double-publishes a row.
	 */
	@Lock(LockModeType.PESSIMISTIC_WRITE)
	@Query(value = "SELECT * FROM outbox_events WHERE published_at IS NULL ORDER BY created_at LIMIT :batchSize FOR UPDATE SKIP LOCKED",
			nativeQuery = true)
	List<OutboxEventEntity> findUnpublishedBatch(int batchSize);
}
