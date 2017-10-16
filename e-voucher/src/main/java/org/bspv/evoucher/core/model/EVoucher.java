/**
 * 
 */
package org.bspv.evoucher.core.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.beans.BeanUtils;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;

/**
 * Immutable representation of an e-voucher.
 */
@ToString
@EqualsAndHashCode(of = { "id" })
public final class EVoucher implements Serializable {

	/**
	 * Generated Serial UID.
	 */
	private static final long serialVersionUID = 6158446847124861174L;

	/**
	 * 
	 * Event status.
	 *
	 */
	public enum EVoucherStatus {
		ACTIVE, // The e-voucher can be updated, printed and sent, cancelled or archived
		CANCELLED, // No action available from this status
		ARCHIVED; // No action available from this status
	}

	/**
	 * Builder class for {@link EVoucher}.
	 * 
	 */
	public static class Builder {
		/** @see EVoucher#id */
		private UUID id;
		/** @see EVoucher#version */
		private Long version;
		/** @see EVoucher#createdBy */
		private UUID createdBy;
		/** @see EVoucher#createdDate */
		private LocalDateTime createdDate;
		/** @see EVoucher#lastModifiedDate */
		private LocalDateTime lastModifiedDate;
		/** @see EVoucher#lastModifiedBy */
		private UUID lastModifiedBy;
		/** @see EVoucher#name */
		private String name;
		/** @see EVoucher#amount */
		private BigDecimal amount;
		/** @see EVoucher#email */
		private String email;
		/** @see EVoucher#requestDate */
		private LocalDateTime requestDate;
		/** @see EVoucher#status */
		private EVoucherStatus status;
		/**  @see EVoucher#team */
		private Team team;

		/**
		 * Constructor of the builder.
		 * 
		 */
		private Builder() {
			super();
		}

		/**
		 * build the e-voucher calling the constructor or the EVoucher class.
		 * 
		 * @return new instance of {@link EVoucher}
		 */
		public EVoucher build() {
			return new EVoucher(this);
		}

		/**
		 * 
		 * @param id
		 * @return this builder instance
		 */
		public Builder withId(UUID id) {
			this.id = id;
			return this;
		}

		/**
		 * 
		 * @param version
		 * @return this builder instance
		 */
		public Builder withVersion(Long version) {
			this.version = version;
			return this;
		}

		/**
		 * 
		 * @param createdBy
		 * @return this builder instance
		 */
		public Builder createdBy(UUID createdBy) {
			this.createdBy = createdBy;
			return this;
		}

		/**
		 * 
		 * @param createdDate
		 * @return this builder instance
		 */
		public Builder createdDate(LocalDateTime createdDate) {
			this.createdDate = createdDate;
			return this;
		}

		/**
		 * 
		 * @param lastModifiedBy
		 * @return this builder instance
		 */
		public Builder lastModifiedBy(UUID lastModifiedBy) {
			this.lastModifiedBy = lastModifiedBy;
			return this;
		}

		/**
		 * 
		 * @param lastModifiedDate
		 * @return this builder instance
		 */
		public Builder lastModifiedDate(LocalDateTime lastModifiedDate) {
			this.lastModifiedDate = lastModifiedDate;
			return this;
		}

		/**
		 * 
		 * @param name
		 * @return this builder instance
		 */
		public Builder withName(String name) {
			this.name = name;
			return this;
		}

		/**
		 * 
		 * @param amount
		 * @return this builder instance
		 */
		public Builder withAmount(BigDecimal amount) {
			this.amount = amount;
			return this;
		}

		/**
		 * 
		 * @param email
		 * @return this builder instance
		 */
		public Builder withEmail(String email) {
			this.email = email;
			return this;
		}

		/**
		 * 
		 * @param requestDate
		 * @return this builder instance
		 */
		public Builder requestDate(LocalDateTime requestDate) {
			this.requestDate = requestDate;
			return this;
		}

		/**
		 * @param status
		 *            the status to save. the status to set (Not null)
		 * @return this builder instance
		 * @throws an
		 *             {@link IllegalArgumentException} is status is null.
		 */
		public Builder withStatus(@NonNull EVoucherStatus status) {
			this.status = status;
			return this;
		}
		
		/**
		 * 
		 * @param team The team issuing the e-voucher
		 * @return this builder instance
		 */
		public Builder withTeam (Team team) {
			this.team = team;
			return this;
		}

	}

	/**
	 * Identifier of this e-voucher.
	 */
	@Getter
	private final UUID id;

	/**
	 * Version number for optimistic locking.
	 */
	@Getter
	private final Long version;
	/**
	 * Auditor who creates this entity.
	 */
	@Getter
	private final UUID createdBy;

	/**
	 * Date of creation.
	 */
	@Getter
	private final LocalDateTime createdDate;

	/**
	 * Date of latest change.
	 */
	@Getter
	private final LocalDateTime lastModifiedDate;

	/**
	 * Auditor who made the latest change on this entity.
	 */
	@Getter
	private final UUID lastModifiedBy;

	/**
	 * Name of the person or the company targeted by this voucher.
	 */
	@Getter
	private final String name;

	/**
	 * Amount declared on the e-voucher.
	 */
	@Getter
	private final BigDecimal amount;

	/**
	 * Email of the person or the company targeted by this voucher.
	 */
	@Getter
	private final String email;

	/**
	 * Date and time of the voucher's request.
	 */
	@Getter
	private final LocalDateTime requestDate;

	/**
	 * Status of the e-voucher for soft deletion.
	 */
	@Getter
	private final EVoucherStatus status;
	
	/**
	 * Team issuing the voucher.
	 */
	@Getter
	private final Team team;

	/**
	 * Default constructor.
	 */
	private EVoucher(Builder builder) {
		super();
		this.id = builder.id != null ? builder.id : UUID.randomUUID();
		this.version = builder.version != null ? builder.version : 0L;
		this.createdBy = builder.createdBy;
		this.createdDate = builder.createdDate != null ? builder.createdDate : LocalDateTime.now();
		this.lastModifiedDate = builder.lastModifiedDate;
		this.lastModifiedBy = builder.lastModifiedBy;
		this.name = builder.name;
		this.amount = builder.amount != null ? builder.amount : new BigDecimal(0);
		this.email = builder.email;
		this.requestDate = builder.requestDate  != null ? builder.requestDate : this.createdDate;
		this.status = builder.status != null ? builder.status : EVoucherStatus.ACTIVE;
		this.team = builder.team ;
	}

	/**
	 * Static method to access the builder.
	 * 
	 * @return a new {@link Builder} instance.
	 */
	public static Builder builder() {
		return new Builder();
	}

	/**
	 * Static method to access the builder.
	 * 
	 * @param event
	 *            Event to use as a source
	 * @return a new {@link Builder} instance.
	 */
	public static Builder builderBasedOn(EVoucher eVoucher) {
		Builder builder = new Builder();
		BeanUtils.copyProperties(eVoucher, builder, "uuid");
		return builder;
	}

	/**
	 * Indicate whether the eVoucher is already persisted or not.
	 * 
	 * @return true if the eVoucher has been already saved in the database,
	 *         false otherwise.
	 */
	@JsonIgnore
	public boolean isNew() {
		return this.version == null || this.version.equals(0L);
	}

	/**
	 * Compute a simple hash of the eVoucher.
	 * 
	 * @return
	 */
	public int computeHash() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result + ((amount == null) ? 0 : amount.hashCode());
		result = prime * result + ((requestDate == null) ? 0 : requestDate.hashCode());
		return result;
	}

}
