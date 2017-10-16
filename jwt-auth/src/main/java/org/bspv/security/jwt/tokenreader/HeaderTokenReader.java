/**
 * 
 */
package org.bspv.security.jwt.tokenreader;

import javax.servlet.http.HttpServletRequest;

import org.bspv.security.jwt.TokenProcessorProperties;
import org.bspv.security.jwt.tokenwriter.HeaderTokenWriter;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 */
public class HeaderTokenReader implements TokenReader {

	@Autowired
	private TokenProcessorProperties properties;
	
	/*
	 * (non-Javadoc)
	 * @see org.bspv.security.jwt.finder.TokenFinder#find(javax.servlet.http.HttpServletRequest)
	 */
	@Override
	public String find(HttpServletRequest request) {
		String authorizationHeader = request.getHeader(properties.getAuthorizationHeaderName());
		return authorizationHeader != null ? authorizationHeader.replace(HeaderTokenWriter.TOKEN_PREFIX, "") : "";
	}


}
