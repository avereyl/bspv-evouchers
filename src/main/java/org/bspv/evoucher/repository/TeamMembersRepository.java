/**
 * 
 */
package org.bspv.evoucher.repository;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

import org.bspv.evoucher.core.model.Team;
import org.bspv.evoucher.core.model.User;

/**
 *
 */
public interface TeamMembersRepository {

    /**
	 * Find teammates of the user at the given date.
	 * @param user
	 * @param now
	 * @return
	 */
	Set<UUID> findTeammates(User user, LocalDateTime now);
	/**
	 * Find members of the team at the given date.
	 * @param team
	 * @param now
	 * @return
	 */
	Set<UUID> findMembers(Team team, LocalDateTime now);
	/**
	 * Find team of the user at the given date.
	 * @param user
	 * @param now
	 * @return
	 */
	Team findTeam(User user, LocalDateTime now);
	/**
	 * Find team of the user with given id at the given date.
	 * @param user
	 * @param now
	 * @return
	 */
	Team findTeam(UUID uuid, LocalDateTime now);
}
