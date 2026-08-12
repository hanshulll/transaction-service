package com.paymentledger.account.infrastructure.persistence;

import jakarta.persistence.*;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.JdbcType;
import org.hibernate.annotations.SourceType;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.type.descriptor.jdbc.CharJdbcType;

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
	@GeneratedValue(strategy = GenerationType.UUID)
	@Column(updatable = false, nullable = false)
	private UUID id;

	@Column(name = "owner_name", nullable = false)
	private String ownerName;

	@JdbcType(CharJdbcType.class)
	@Column(nullable = false, columnDefinition = "char(3)")
	private String currency;

	@PositiveOrZero(message = "Balance cannot be negative")
	@Column(nullable = false, precision = 19, scale = 4)
	private BigDecimal balance = BigDecimal.ZERO;

	@Version
	@Column(nullable = false)
	private Long version;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private AccountStatus status;

	@CreationTimestamp
	@Column(name = "created_at",updatable = false, nullable = false, columnDefinition = "TIMESTAMP WITH TIME ZONE")
	private Instant createdAt;

	@UpdateTimestamp
	@Column(name = "updated_at", nullable = false, columnDefinition = "TIMESTAMP WITH TIME ZONE")
	private Instant updatedAt;
}
