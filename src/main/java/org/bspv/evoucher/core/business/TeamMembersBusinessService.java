/**
 * 
 */
package org.bspv.evoucher.core.business;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

import org.bspv.evoucher.core.model.Team;
import org.bspv.evoucher.core.model.User;
import org.bspv.evoucher.repository.TeamMembersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 */
@Service
public class TeamMembersBusinessService {

	/**
	 * Repository handling users.
	 */
	@Autowired
	private TeamMembersRepository userRepository;
	
	/**
	 * Find current team mates of the given user.
	 * The User id is not in the set.
	 * @param user
	 * @return Set of {@link User}
	 */
	public Set<UUID> findTeammates(User user) {
		return userRepository.findTeammates(user, LocalDateTime.now());
	}
	
	/**
	 * Find all current members of a team.
	 * @param team
	 * @return Set of {@link User}
	 */
	public Set<UUID> findMembers(Team team) {
		return userRepository.findMembers(team, LocalDateTime.now());
	}
	/**
     * Find current team of the user.
     * @param user
     * @param now
     * @return
     */
    public Team findTeam(User user) {
        return userRepository.findTeam(user, LocalDateTime.now());
    }
    /**
     * Find current team of the user with given id.
     * @param user
     * @param now
     * @return
     */
    public Team findTeam(UUID uuid) {
        return userRepository.findTeam(uuid, LocalDateTime.now());
    }


	
}
