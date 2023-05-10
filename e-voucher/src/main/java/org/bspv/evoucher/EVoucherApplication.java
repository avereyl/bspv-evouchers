package org.bspv.evoucher;

import org.bspv.evoucher.config.listener.HsqldbServletContextListener;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.flyway.FlywayAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Profile;
import org.springframework.web.context.WebApplicationContext;


/**
 * 
 * FlywayAutoConfiguration is disabled. Flyway is only used with Maven profile
 * 'generate' and linked to Jooq class generation.
 */
@ServletComponentScan
@SpringBootApplication(exclude = { FlywayAutoConfiguration.class })
public class EVoucherApplication {

	/**
	 * Main method.
	 *
	 * @param args
	 */
	public static void main(String[] args) {
		SpringApplication.run(EVoucherApplication.class, args);
	}

	/**
	 * Register a servlet context listener to start/stop a HSQLDB is no Spring profile defined.
	 * Consider using {@link Conditional} for more complex activation.
	 * @return
	 */
	@Bean
	@Profile("default")
	@ConditionalOnProperty(name="spring.datasource.fallback.enabled", matchIfMissing=true)
	public HsqldbServletContextListener hsqldbServletContextListener() {
		return new HsqldbServletContextListener();
	}

}
