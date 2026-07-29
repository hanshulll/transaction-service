package com.paymentledger.account.infrastructure.persistence;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "accounts")
public class AccountEntity {

	@Id
	private UUID id;

	@Column(name = "owner_name", nullable = false)
	private String ownerName;

	@Column(nullable = false)
	private String currency;

	@Column(nullable = false)
	private BigDecimal balance;

	@Version
	@Column(nullable = false)
	private long version;

	@Column(nullable = false)
	private String status;

	@Column(name = "created_at", nullable = false)
	private Instant createdAt;

	@Column(name = "updated_at", nullable = false)
	private Instant updatedAt;
}
