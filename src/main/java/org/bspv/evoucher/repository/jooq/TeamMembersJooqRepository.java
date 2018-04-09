/**
 * 
 */
package org.bspv.evoucher.repository.jooq;

import static org.bspv.evouchers.jooq.tables.TeamMembers.TEAM_MEMBERS;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import org.bspv.evoucher.core.model.Team;
import org.bspv.evoucher.core.model.User;
import org.bspv.evoucher.repository.TeamMembersRepository;
import org.bspv.evoucher.repository.jooq.converter.RecordConverterFactory;
import org.bspv.evouchers.jooq.tables.records.TeamMembersRecord;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import lombok.extern.slf4j.Slf4j;

/**
 *
 */
@Slf4j
@Repository
@Transactional
public class TeamMembersJooqRepository implements TeamMembersRepository {

	/**
	 * Jooq DSL context.
	 */
	private final DSLContext dslContext;

	/**
	 * Constructor.
	 * 
	 * @param dslContext
	 */
	public TeamMembersJooqRepository(DSLContext dslContext) {
		super();
		this.dslContext = dslContext;
	}


	/*
	 * (non-Javadoc)
	 * 
	 * @see org.bspv.evoucher.repository.UserRepository#findTeammates(org.bspv.evoucher.core.model.User,
	 * java.time.LocalDateTime)
	 */
	@Override
	public Set<UUID> findTeammates(final User user, final LocalDateTime date) {
		LocalDateTime validityDate = (date == null) ? LocalDateTime.now() : date;
//		@formatter:off
		return this.dslContext
			.select(TEAM_MEMBERS.USER_ID)
			.from(TEAM_MEMBERS)
			.where(TEAM_MEMBERS.TEAM_NUMBER.eq(user.getTeam().getNumber()))
			.and(TEAM_MEMBERS.DISTRIBUTION_YEAR.eq(user.getTeam().getYear()))
			.and(TEAM_MEMBERS.VALIDITY_START.le(validityDate))
			.and(TEAM_MEMBERS.VALIDITY_END.greaterThan(validityDate))
			.and(TEAM_MEMBERS.USER_ID.ne(user.getId()))
			.fetch()
			.stream()
			.map(r -> r.getValue(TEAM_MEMBERS.USER_ID))
			.collect(Collectors.toSet());
//		@formatter:on
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.bspv.evoucher.repository.UserRepository#findMembers(org.bspv.evoucher.core.model.Team,
	 * java.time.LocalDateTime)
	 */
	@Override
	public Set<UUID> findMembers(final Team team, final LocalDateTime date) {
		Set<UUID> teamMembers = new HashSet<>();
		LocalDateTime validityDate = (date == null) ? LocalDateTime.now() : date;
		if (team == null) {
			log.info("No given team !");
			return teamMembers;
		}
//		@formatter:off
		teamMembers.addAll(
		        this.dslContext
		        .select(TEAM_MEMBERS.USER_ID)
		        .from(TEAM_MEMBERS)
		        .where(TEAM_MEMBERS.TEAM_NUMBER.eq(team.getNumber()))
		        .and(TEAM_MEMBERS.DISTRIBUTION_YEAR.eq(team.getYear()))
		        .and(TEAM_MEMBERS.VALIDITY_START.le(validityDate))
		        .and(TEAM_MEMBERS.VALIDITY_END.greaterThan(validityDate))
		        .fetch()
		        .stream()
		        .map(r -> r.getValue(TEAM_MEMBERS.USER_ID))
		        .collect(Collectors.toSet()));
//		@formatter:on
		return teamMembers;
	}


    /* (non-Javadoc)
     * @see org.bspv.evoucher.repository.UserRepository#findTeam(org.bspv.evoucher.core.model.User, java.time.LocalDateTime)
     */
    @Override
    public Team findTeam(User user, LocalDateTime validityDate) {
        TeamMembersRecord record = this.dslContext
            .selectFrom(TEAM_MEMBERS)
            .where(TEAM_MEMBERS.USER_ID.eq(user.getId()))
            .and(TEAM_MEMBERS.VALIDITY_START.le(validityDate))
            .and(TEAM_MEMBERS.VALIDITY_END.greaterThan(validityDate))
            .fetchOne();
        return RecordConverterFactory.convert(record);
    }
    /* (non-Javadoc)
     * @see org.bspv.evoucher.repository.UserRepository#findTeam(org.bspv.evoucher.core.model.User, java.time.LocalDateTime)
     */
    @Override
    public Team findTeam(UUID uuid, LocalDateTime validityDate) {
        TeamMembersRecord record = this.dslContext
                .selectFrom(TEAM_MEMBERS)
                .where(TEAM_MEMBERS.USER_ID.eq(uuid))
                .and(TEAM_MEMBERS.VALIDITY_START.le(validityDate))
                .and(TEAM_MEMBERS.VALIDITY_END.greaterThan(validityDate))
                .fetchOne();
        return RecordConverterFactory.convert(record);
    }

}
