/**
 * 
 */
package org.bspv.evoucher.core.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.beans.BeanUtils;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;

/**
 * This class represents an immutable event that can occurs for an e-voucher.
 * 
 */
@ToString
@EqualsAndHashCode(of = { "eVoucherUuid", "eventType", "createdDate" })
public final class EVoucherEvent implements Serializable {

	/**
	 * Serial UID.
	 */
	private static final long serialVersionUID = 8288770698162807188L;

	/**
	 * Key of the event's type.
	 *
	 */
	public enum EVoucherEventType {

		ACK, //
		CREATION, //
		UPDATE, //
		CANCELLATION, //
		PRINTING, //
		DISPATCH, //
		ARCHIVING, //
		NO_OPERATION,//
		ERROR,//
		UNKNOWN;//
	}

	/**
	 * Builder class for {@link EVoucherEvent}.
	 * 
	 */
	public static class Builder {

		/** @see EVoucherEvent#uuid */
		private Long id;
		/** @see EVoucherEvent#eVoucherUuid */
		private final UUID eVoucherUuid;
		/** @see EVoucherEvent#type */
		private EVoucherEventType eventType = EVoucherEventType.UNKNOWN;
		/** @see EVoucherEvent#createdBy */
		private UUID createdBy;
		/** @see EVoucherEvent#createdDate */
		private LocalDateTime createdDate;

		/**
		 * Constructor of the builder.
		 * 
		 * @param eVoucherUUID
		 */
		private Builder(UUID eVoucherUuid) {
			super();
			this.eVoucherUuid = eVoucherUuid;
		}

		/**
		 * build the event calling the constructor or the EVoucherEvent.
		 * 
		 * @return new instance of {@link EVoucherEvent}
		 */
		public EVoucherEvent build() {
			return new EVoucherEvent(this);
		}

		/**
		 * Set the event id (if known).
		 * 
		 * @param id
		 *            The id of the event.
		 * @return this builder.
		 */
		public Builder withId(Long id) {
			this.id = id;
			return this;
		}

		/**
		 * Set the eventKey. @see {@link EventKey}
		 * 
		 * @param eventType
		 *            The type of the event (Not null).
		 * @return this builder.
		 * @throws a
		 *             new {@link IllegalArgumentException} if the eventKey is
		 *             null (lombok)
		 */
		public Builder withKey(@NonNull EVoucherEventType eventType) {
			this.eventType = eventType;
			return this;
		}

		/**
		 * Set the createdBy attribute.
		 * 
		 * @param auditor
		 *            The auditor responsible for the event creation.
		 * @return this builder.
		 */
		public Builder createdBy(UUID auditor) {
			this.createdBy = auditor;
			return this;
		}

		/**
		 * Set the createdDate attribute.
		 * 
		 * @param dateTime
		 *            Date of event creation.
		 * @return this builder.
		 */
		public Builder createdDate(LocalDateTime dateTime) {
			this.createdDate = dateTime;
			return this;
		}

	}

	/**
	 * Identifier of this e-voucher event.
	 */
	@Getter
	private final Long id;

	/**
	 * UUID of the eVoucher.
	 */
	@Getter
	private final UUID eVoucherUuid;

	/**
	 * Event key.
	 */
	@Getter
	private final EVoucherEventType eventType;

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
	 * Constructor. Builder pattern.
	 * 
	 * @param builder
	 */
	private EVoucherEvent(Builder builder) {
		super();
		this.id = builder.id != null ? builder.id : 0L;
		this.createdDate = builder.createdDate != null ? builder.createdDate : LocalDateTime.now();
		this.createdBy = builder.createdBy;
		this.eVoucherUuid = builder.eVoucherUuid;
		this.eventType = builder.eventType;
	}

	/**
	 * Static method to access the builder.
	 * 
	 * @param eVoucherUUID
	 *            uuid of the targeted voucher
	 * @return a new {@link Builder} instance.
	 */
	public static Builder builderFor(UUID eVoucherUUID) {
		return new Builder(eVoucherUUID);
	}

	/**
	 * Static method to access the builder.
	 * 
	 * @param event
	 *            Event to use as a source
	 * @return a new {@link Builder} instance.
	 */
	public static Builder builderBasedOn(EVoucherEvent event) {
		Builder builder = new Builder(event.getEVoucherUuid());
		BeanUtils.copyProperties(event, builder, "uuid");
		return builder;
	}

}
