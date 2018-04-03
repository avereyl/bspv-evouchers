/**
 * 
 */
package org.bspv.evoucher.repository;

import java.time.LocalDateTime;
import java.util.Set;

import org.bspv.evoucher.core.model.Team;
import org.bspv.evoucher.core.model.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

/**
 *
 */
public interface UserRepository {

	/**
	 * 
	 * @param username
	 * @return
	 * @throws UsernameNotFoundException
	 */
	User loadByUsername (String username) throws UsernameNotFoundException;
	/**
	 * 
	 * @param username
	 * @param date
	 * @return
	 * @throws UsernameNotFoundException
	 */
	User loadByUsername (String username, LocalDateTime date) throws UsernameNotFoundException;
	/**
	 * Find teammates of the user at the given date.
	 * @param user
	 * @param now
	 * @return
	 */
	Set<User> findTeammates(User user, LocalDateTime now);
	/**
	 * Find members of the team at the given date.
	 * @param team
	 * @param now
	 * @return
	 */
	Set<User> findMembers(Team team, LocalDateTime now);
}
