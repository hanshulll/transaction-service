package com.paymentledger.ledgerconsumer.infrastructure.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface LedgerEntryRepository extends JpaRepository<LedgerEntryEntity, UUID> {

	List<LedgerEntryEntity> findByAccountId(UUID accountId);
}
