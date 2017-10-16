/**
 * 
 */
package org.bspv.security.jwt;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.bspv.security.jwt.mapper.TokenToUserDetailsMapper;
import org.bspv.security.jwt.tokenreader.TokenReader;
import org.bspv.security.jwt.tokenwriter.TokenWriter;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.impl.TextCodec;
import lombok.extern.slf4j.Slf4j;

/**
 *
 */
@Slf4j
@Service
public class TokenAuthenticationService implements InitializingBean {

	@Autowired
	private TokenProcessorProperties properties;

	@SuppressWarnings("rawtypes")
	@Autowired
	private TokenToUserDetailsMapper tokenToUserDetailsMapper;

	@Autowired
	private final List<TokenReader> tokenFinders = new ArrayList<>();
	
	@Autowired
	private TokenWriter tokenWriter;
	
	/**
	 * 
	 * @param response
	 * @param userDetails
	 */
	public void addAuthenticationToken(HttpServletResponse response, UserDetails userDetails) {
		LocalDateTime now = LocalDateTime.now();
		Date expirationDate = Date
				.from(now.toInstant(ZoneOffset.UTC).plusMillis(properties.getDefaultExpirationTime()));
		@SuppressWarnings("unchecked")
		Claims claims = tokenToUserDetailsMapper.toClaims(userDetails);
		SignatureAlgorithm algorithm = SignatureAlgorithm.forName(this.properties.getSignatureAlgorithmName());
//		@formatter:off
		String JWT = Jwts
 				.builder()
 				.setClaims(claims)
 				.setIssuedAt(Date.from(now.toInstant(ZoneOffset.UTC)))
				.setExpiration(expirationDate)
				.signWith(algorithm, TextCodec.BASE64.encode(this.properties.getSecret()))
				.compact();
//		@formatter:on
		tokenWriter.write(JWT, response);
	}

	/**
	 * 
	 * @param request
	 * @return
	 */
	public Authentication checkAuthentication(HttpServletRequest request, HttpServletResponse response) {
		// load authorization token
		Authentication result = null;
		String token = findToken(request);
		if (!StringUtils.isEmpty(token)) {
			try {
				// check claims validity
				Claims claims = Jwts.parser()
						.setSigningKey(TextCodec.BASE64.encode(this.properties.getSecret()))
						.parseClaimsJws(token)
						.getBody();
				
				// build userDetails from claims
				UserDetails user = tokenToUserDetailsMapper.toUserDetails(claims);
				if (user != null) {
					result = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
				}
			} catch (Exception e) {
				log.warn("Invalid JWT Token", e);
			}
//			@formatter:on
		}
		return result;
	}

	/**
	 * Try to load the JWT token from the request. From Authorization header first and then from cookies
	 * 
	 * @param request
	 *            The request
	 * @return The JWT token if any, an empty string otherwise
	 */
	protected String findToken(HttpServletRequest request) {
		return this.tokenFinders
				.stream()
				.map(finder -> finder.find(request))
				.filter( token -> !StringUtils.isEmpty(token))
				.findFirst()
				.orElse("");
	}

	/* (non-Javadoc)
	 * @see org.springframework.beans.factory.InitializingBean#afterPropertiesSet()
	 */
	@Override
	public void afterPropertiesSet() throws Exception {
		// warn if no token reader is found
		if (tokenFinders.size() == 0) {
			log.warn("No token reader found -> no way to read JWT authorization credentials.");
		}
		
	}
	
	
}
