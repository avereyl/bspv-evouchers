/**
 * 
 */
package org.bspv.evoucher.core.business;

import java.time.LocalDateTime;
import java.util.Set;

import org.bspv.evoucher.core.model.Team;
import org.bspv.evoucher.core.model.User;
import org.bspv.evoucher.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 */
@Service
public class UserBusinessService {

	/**
	 * Repository handling users.
	 */
	@Autowired
	private UserRepository userRepository;
	
	/**
	 * Find current team mates of the given user.
	 * The User is not in the set.
	 * @param user
	 * @return Set of {@link User}
	 */
	public Set<User> findTeammates(User user) {
		return userRepository.findTeammates(user, LocalDateTime.now());
	}
	
	/**
	 * Find all current members of a team.
	 * @param team
	 * @return Set of {@link User}
	 */
	public Set<User> findMembers(Team team) {
		return userRepository.findMembers(team, LocalDateTime.now());
	}

}
