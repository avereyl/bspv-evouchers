package org.bspv.evoucher.config.security;

import org.bspv.security.jwt.tokenwriter.CookieTokenWriter;
import org.bspv.security.jwt.tokenwriter.TokenWriter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 
 *
 */
@Configuration
public class SecurityConfig {

	/**
	 * Token writer bean.
	 */
	@Bean
	public TokenWriter tokenWriter() {
		return new CookieTokenWriter();
	}
	
	
}
