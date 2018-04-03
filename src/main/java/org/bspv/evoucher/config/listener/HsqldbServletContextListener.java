/**
 * 
 */
package org.bspv.evoucher.config.listener;

import java.io.ByteArrayOutputStream;
import java.io.PrintWriter;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.hsqldb.Server;
import org.springframework.beans.factory.annotation.Value;

import lombok.extern.slf4j.Slf4j;

/**
 * This Servlet Context listener is responsible for starting(and stopping) a
 * HSQLDB database. (used in test and development mode / without standalone db
 * installed)
 */
@Slf4j
public class HsqldbServletContextListener implements ServletContextListener {

	private static final String URL_PREFIX = "jdbc:hsqldb:hsql://";

	protected Server server;

	@Value("${spring.datasource.driver-class-name}")
	private String driver;

	@Value("${spring.datasource.username}")
	private String username;

	@Value("${spring.datasource.password}")
	private String password;

	@Value("${spring.datasource.url}")
	private String connectionURL;

	@Value("${spring.datasource.path}")
	private String path;

	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		log.debug("Stopping database...");
		if (server != null) {
			server.shutdown();
		}
		log.debug("Database stopped.");
	}

	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		log.debug("Starting database...");

		String s = connectionURL.replaceFirst(URL_PREFIX, "");
		String host = s.substring(0, s.indexOf(':'));
		String declaredPort = s.substring(s.indexOf(':') + 1, s.indexOf('/'));
		String dbname = s.substring(s.indexOf('/') + 1);
		
		Integer port = Integer.valueOf(declaredPort);

		server = new Server();
		server.setDaemon(true);
		server.setNoSystemExit(true);
		server.setSilent(true);
		server.setLogWriter(slf4jPrintWriter());
		server.setErrWriter(slf4jPrintWriter());

		server.setAddress(host);
		server.setPort(port);
		server.setDatabasePath(0, path);
		server.setDatabaseName(0, dbname);

		server.start();
		log.debug("Database started.");
	}

	public boolean isServerRunning() {
		return server != null && !server.isNotRunning();
	}
	/**
	 * To redirect this server output to slf4j output
	 * 
	 * @return
	 */
	private PrintWriter slf4jPrintWriter() {
		return new PrintWriter(new ByteArrayOutputStream()) {
			@Override
			public void println(final String x) {
				log.debug(x);
			}
		};
	}

}
