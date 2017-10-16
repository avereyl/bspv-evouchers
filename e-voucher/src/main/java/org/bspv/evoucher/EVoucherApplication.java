package org.bspv.evoucher;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.flyway.FlywayAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.web.context.WebApplicationContext;

/**
 * 
 * FlywayAutoConfiguration is disabled. Flyway is only used with Maven profile 'generate' and linked to Jooq class
 * generation.
 */
@SpringBootApplication(exclude = { FlywayAutoConfiguration.class })
public class EVoucherApplication extends SpringBootServletInitializer {

	/**
	 * Main method.
	 *
	 * @param args
	 */
	public static void main(String[] args) {
		SpringApplication.run(EVoucherApplication.class, args);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.springframework.boot.web.support.SpringBootServletInitializer#run(org.springframework.boot.SpringApplication)
	 */
	@Override
	protected WebApplicationContext run(SpringApplication application) {
		return super.run(application);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.springframework.boot.context.web.SpringBootServletInitializer#
	 * configure(org.springframework.boot.builder.SpringApplicationBuilder)
	 */
	@Override
	protected SpringApplicationBuilder configure(final SpringApplicationBuilder application) {
		return application.sources(EVoucherApplication.class).initializers();
	}

}
