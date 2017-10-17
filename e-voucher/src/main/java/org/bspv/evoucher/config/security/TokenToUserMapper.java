/**
 * 
 */
package org.bspv.evoucher.config.security;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import org.bspv.evoucher.core.model.Team;
import org.bspv.evoucher.core.model.User;
import org.bspv.security.jwt.mapper.TokenToUserDetailsMapper;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

/**
 *
 */
@Component
public class TokenToUserMapper implements TokenToUserDetailsMapper<User> {

	public static final String USER_ID_CLAIM_NAME = "id";
	public static final String TEAM_CLAIM_NAME = "team";
	public static final String VERSION_CLAIM_NAME = "version";

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.bspv.security.jwt.TokenToUserDetailsMapper#toUserDetails(io.jsonwebtoken.
	 * Claims)
	 */
	@Override
	@SuppressWarnings("unchecked")
	public User toUserDetails(final Claims claims) {
		List<String> scopes = claims.get(AUTHORITIES_CLAIM_NAME, List.class);
		List<GrantedAuthority> authorities = scopes.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList());
		Map<String, Integer> teamMap = claims.get(TEAM_CLAIM_NAME, Map.class);
		Team team = Team.builder().withNumber(teamMap.get(Team.NUMBER_FIELD)).forYear(teamMap.get(Team.YEAR_FIELD)).build();
		return User.builder().withId(UUID.fromString(claims.getId())).withUserName(claims.getSubject()).withAuthorities(authorities).withTeam(team)
				.withVersion(claims.get(VERSION_CLAIM_NAME, Integer.class).longValue()).build();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.bspv.security.jwt.TokenToUserDetailsMapper#toClaims(org.springframework.
	 * security.core.userdetails. UserDetails)
	 */
	@Override
	public Claims toClaims(final User user) {
		Claims claims = Jwts.claims().setSubject(user.getUsername());
		claims.setId(user.getId().toString());
		claims.put(AUTHORITIES_CLAIM_NAME, user.getAuthorities().stream().map(Object::toString).collect(Collectors.toList()));
		claims.put(TEAM_CLAIM_NAME, user.getTeam());
		claims.put(VERSION_CLAIM_NAME, user.getVersion());
		return claims;
	}

}
