/**
 * 
 */
package org.bspv.evoucher.repository.jooq.converter;

import org.bspv.evoucher.core.model.EVoucher;
import org.bspv.evoucher.core.model.EVoucher.EVoucherStatus;
import org.bspv.evoucher.core.model.EVoucherEvent;
import org.bspv.evoucher.core.model.EVoucherEvent.EVoucherEventType;
import org.bspv.evoucher.core.model.Team;
import org.bspv.evoucher.core.model.User;
import org.bspv.evouchers.jooq.tables.records.EvoucherEventsRecord;
import org.bspv.evouchers.jooq.tables.records.EvouchersRecord;
import org.bspv.evouchers.jooq.tables.records.TeamMembersRecord;
import org.bspv.evouchers.jooq.tables.records.UsersRecord;

/**
 *
 */
public final class RecordConverterFactory {

	public static EVoucher convert(EvouchersRecord record) {
		return toBuilder(record).build();
	}
	
	public static EVoucher.Builder toBuilder(EvouchersRecord record) {
		return EVoucher
				.builder()
				.withId(record.getId())
				.withVersion(record.getVersion())
				.createdBy(record.getCreatedBy())
				.createdDate(record.getCreatedDate())
				.lastModifiedBy(record.getLastModifiedBy())
				.lastModifiedDate(record.getLastModifiedDate())
				.requestDate(record.getRequestDate())
				.withEmail(record.getEmail())
				.withName(record.getName())
				.withAmount(record.getAmount())
				.withStatus(EVoucherStatus.valueOf(record.getStatus()))
				.withTeam(Team.builder()
						.forYear(record.getDistributionYear())
						.withNumber(record.getTeamNumber())
						.build()
				);
	}
	
	
	public static Team convert(TeamMembersRecord record) {
		return toBuilder(record).build();
	}
	
	public static Team.Builder toBuilder(TeamMembersRecord record) {
		return Team
				.builder()
				.withNumber(record.getTeamNumber())
				.forYear(record.getDistributionYear());
	}
	
	
	public static EVoucherEvent convert(EvoucherEventsRecord record) {
		return toBuilder(record).build();
	}
	
	public static EVoucherEvent.Builder toBuilder(EvoucherEventsRecord record) {
		return EVoucherEvent
				.builderFor(record.getEvoucherId())
				.withId(record.getId())
				.withKey(EVoucherEventType.valueOf(record.getEventType()))
				.createdBy(record.getCreatedBy())
				.createdDate(record.getCreatedDate());
	}
	
	public static User convert(UsersRecord record) {
		return toBuilder(record).build();
	}
	
	public static User.Builder toBuilder(UsersRecord record) {
		return User
				.builder()
				.withId(record.getId())
				.withVersion(record.getVersion())
				.withUserName(record.getUsername())
				.withEmail(record.getEmail())
				.withKey(record.getSecretkey())
				.enable(record.getEnabled());
	}
	

	/**
	 * Private constructor. This class gathers only static method.
	 */
	private RecordConverterFactory() {
		super();
	}
}
