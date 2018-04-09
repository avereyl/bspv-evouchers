/**
 * 
 */
package org.bspv.evoucher.config.security;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.bspv.evoucher.core.model.Team;
import org.bspv.evoucher.core.model.User;
import org.bspv.security.model.ServiceGrantedAuthority;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;

/**
 *
 */
@Slf4j
@Component
public class TokenToUserMapper extends org.bspv.security.jwt.mapper.TokenToUserMapper {
    
    @Autowired
    private UserTeamCache userTeamCache;
    
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.bspv.security.jwt.TokenToUserDetailsMapper#toUserDetails(io.jsonwebtoken.
	 * Claims)
	 */
	@Override
	public User toUserDetails(final Claims claims) {
        List<ServiceGrantedAuthority> authorities = getAuthorithiesFromClaims(claims);
		
        UUID uuid = UUID.fromString(claims.getId());
		Team team = userTeamCache.getUserTeam(uuid);
		if (team == null) {
		    log.error("User must be in a team ! Please check your config.");
		    team = Team.builder().withNumber(0).forYear(LocalDateTime.now().getYear()).build();
		    log.warn("using fallback team {}", team);
		}
		return User.builder()
		        .id(uuid)
		        .username(claims.getSubject())
		        .authorities(authorities)
		        .team(team)
				.version(getVersionFromClaims(claims))
				.build();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.bspv.security.jwt.TokenToUserDetailsMapper#toClaims(org.springframework.
	 * security.core.userdetails. UserDetails)
	 */
	@Override
	public Claims toClaims(final org.bspv.security.model.User user) {
	    throw new UnsupportedOperationException("This service only read JWT token.");
	}

}
