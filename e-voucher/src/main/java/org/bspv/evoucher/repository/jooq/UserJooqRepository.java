/**
 * 
 */
package org.bspv.evoucher.repository.jooq;

import static org.bspv.evouchers.jooq.tables.Authorities.AUTHORITIES;
import static org.bspv.evouchers.jooq.tables.TeamMembers.TEAM_MEMBERS;
import static org.bspv.evouchers.jooq.tables.Users.USERS;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.stream.Collectors;

import org.bspv.evoucher.core.model.Team;
import org.bspv.evoucher.core.model.User;
import org.bspv.evoucher.repository.UserRepository;
import org.bspv.evoucher.repository.jooq.converter.RecordConverterFactory;
import org.bspv.evouchers.jooq.tables.records.TeamMembersRecord;
import org.bspv.evouchers.jooq.tables.records.UsersRecord;
import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.Result;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import lombok.extern.slf4j.Slf4j;

/**
 *
 */
@Slf4j
@Repository
@Transactional
public class UserJooqRepository implements UserRepository {

	/**
	 * Jooq DSL context.
	 */
	private final DSLContext dslContext;

	/**
	 * Constructor.
	 * 
	 * @param dslContext
	 */
	public UserJooqRepository(DSLContext dslContext) {
		super();
		this.dslContext = dslContext;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.bspv.evoucher.repository.UserRepository#loadByUsername(java.lang.String, java.time.LocalDateTime)
	 */
	@Override
	public User loadByUsername(String username, LocalDateTime date) throws UsernameNotFoundException {
		User user = null;
//		@formatter:off
		Map<UsersRecord, Result<Record>> recordResultMap = 
		this.dslContext
			.select()
			.from(USERS)
			.leftJoin(AUTHORITIES)
			.on(AUTHORITIES.USER_ID.eq(USERS.ID))
			.leftJoin(TEAM_MEMBERS)
			.on(TEAM_MEMBERS.USER_ID.eq(USERS.ID))
			.and(TEAM_MEMBERS.VALIDITY_START.le(date))
			.and(TEAM_MEMBERS.VALIDITY_END.greaterThan(date))
			.where(USERS.USERNAME.eq(username))
			.and(USERS.ENABLED.eq(Boolean.TRUE))
			.fetch()
			.intoGroups(USERS);
//		@formatter:on
		if (recordResultMap.size() != 1) {
			throw new UsernameNotFoundException("User not found by its username.");
		} else {
			Entry<UsersRecord, Result<Record>> entry = recordResultMap.entrySet().iterator().next();
			UsersRecord userRecord = entry.getKey();
			Team team = RecordConverterFactory.convert(entry.getValue().into(TeamMembersRecord.class).get(0));
//		@formatter:off
			Set<GrantedAuthority> authorities = entry.getValue()
					.getValues(AUTHORITIES.AUTHORITY)
					.stream()
					.map(s -> new SimpleGrantedAuthority(s))
					.collect(Collectors.toSet());
//		@formatter:on
			user = RecordConverterFactory.toBuilder(userRecord).withTeam(team).withAuthorities(authorities).build();
		}
		return user;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.bspv.evoucher.repository.UserRepository#loadByUsername(java.lang.String)
	 */
	@Override
	public User loadByUsername(String username) throws UsernameNotFoundException {
		return loadByUsername(username, LocalDateTime.now());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.bspv.evoucher.repository.UserRepository#findTeammates(org.bspv.evoucher.core.model.User,
	 * java.time.LocalDateTime)
	 */
	@Override
	public Set<User> findTeammates(User user, LocalDateTime date) {
		Set<User> teammates;
		date = (date == null) ? LocalDateTime.now() : date;
//		@formatter:off
		Map<UsersRecord, Result<Record>> recordResultMap = 
		this.dslContext
			.select()
			.from(USERS)
			.leftJoin(AUTHORITIES)
			.on(AUTHORITIES.USER_ID.eq(USERS.ID))
			.leftJoin(TEAM_MEMBERS)
			.on(TEAM_MEMBERS.USER_ID.eq(USERS.ID))
			.and(TEAM_MEMBERS.VALIDITY_START.le(date))
			.and(TEAM_MEMBERS.VALIDITY_END.greaterThan(date))
			.where(TEAM_MEMBERS.TEAM_NUMBER.eq(user.getTeam().getNumber()))
			.and(TEAM_MEMBERS.DISTRIBUTION_YEAR.eq(user.getTeam().getYear()))
			.and(USERS.ENABLED.eq(Boolean.TRUE))
			.and(USERS.ID.ne(user.getId()))
			.fetch()
			.intoGroups(USERS);
//		@formatter:on
//		@formatter:off
		teammates = recordResultMap.entrySet()
				.stream()
				.map(entry -> {
					UsersRecord userRecord = entry.getKey();
					Set<GrantedAuthority> authorities = entry.getValue()
							.getValues(AUTHORITIES.AUTHORITY)
							.stream()
							.map(s -> new SimpleGrantedAuthority(s))
							.collect(Collectors.toSet());
//		@formatter:on
					return RecordConverterFactory.toBuilder(userRecord).withTeam(user.getTeam()).withAuthorities(authorities)
							.build();
				}).collect(Collectors.toSet());

		return teammates;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.bspv.evoucher.repository.UserRepository#findMembers(org.bspv.evoucher.core.model.Team,
	 * java.time.LocalDateTime)
	 */
	@Override
	public Set<User> findMembers(Team team, LocalDateTime date) {
		Set<User> teamMembers = new HashSet<>();
		date = (date == null) ? LocalDateTime.now() : date;
		if (team == null) {
			log.info("No given team !");
			return teamMembers;
		}
//		@formatter:off
		Map<UsersRecord, Result<Record>> recordResultMap = 
		this.dslContext
			.select()
			.from(USERS)
			.leftJoin(AUTHORITIES)
			.on(AUTHORITIES.USER_ID.eq(USERS.ID))
			.leftJoin(TEAM_MEMBERS)
			.on(TEAM_MEMBERS.USER_ID.eq(USERS.ID))
			.and(TEAM_MEMBERS.VALIDITY_START.le(date))
			.and(TEAM_MEMBERS.VALIDITY_END.greaterThan(date))
			.where(TEAM_MEMBERS.TEAM_NUMBER.eq(team.getNumber()))
			.and(TEAM_MEMBERS.DISTRIBUTION_YEAR.eq(team.getYear()))
			.and(USERS.ENABLED.eq(Boolean.TRUE))
			.fetch()
			.intoGroups(USERS);
//		@formatter:on
//		@formatter:off
		teamMembers = recordResultMap.entrySet()
				.stream()
				.map(entry -> {
					UsersRecord userRecord = entry.getKey();
					Set<GrantedAuthority> authorities = entry.getValue()
							.getValues(AUTHORITIES.AUTHORITY)
							.stream()
							.map(s -> new SimpleGrantedAuthority(s))
							.collect(Collectors.toSet());
//		@formatter:on
					return RecordConverterFactory.toBuilder(userRecord).withTeam(team).withAuthorities(authorities)
							.build();
				}).collect(Collectors.toSet());

		return teamMembers;
	}

}
