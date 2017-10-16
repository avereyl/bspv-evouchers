/**
 * 
 */
package org.bspv.security.jwt.tokenwriter;

import javax.servlet.http.HttpServletResponse;

import org.bspv.security.jwt.TokenProcessorProperties;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 */
public class HeaderTokenWriter implements TokenWriter {

	public static final String TOKEN_PREFIX = "Bearer ";
	
	@Autowired
	private TokenProcessorProperties properties;
	
	/*
	 * (non-Javadoc)
	 * @see org.bspv.security.jwt.tokenwriter.TokenWriter#write(java.lang.String, javax.servlet.http.HttpServletResponse)
	 */
	@Override
	public void write(String token, HttpServletResponse response) {
		response.addHeader(properties.getAuthorizationHeaderName(), TOKEN_PREFIX + token);
	}

}
