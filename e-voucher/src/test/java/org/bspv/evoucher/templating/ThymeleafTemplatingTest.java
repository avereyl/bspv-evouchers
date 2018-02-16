/**
 * 
 */
package org.bspv.evoucher.templating;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.thymeleaf.dialect.IDialect;
import org.thymeleaf.spring4.dialect.SpringStandardDialect;
import org.thymeleaf.testing.templateengine.context.web.SpringWebProcessingContextBuilder;
import org.thymeleaf.testing.templateengine.engine.TestExecutor;

/**
 * @author BILLAUDG
 *
 */
public class ThymeleafTemplatingTest {
	
	private static TestExecutor executor;
	
	@BeforeClass
	public static void setup() {
		final List<IDialect> dialects = new ArrayList<IDialect>();
		dialects.add(new SpringStandardDialect());
		final SpringWebProcessingContextBuilder springPCBuilder = new SpringWebProcessingContextBuilder();
		springPCBuilder.setApplicationContextConfigLocation(null);
		ThymeleafTemplatingTest.executor = new TestExecutor();
		executor.setProcessingContextBuilder(springPCBuilder);
		executor.setDialects(dialects);
	}

	/**
	 * 
	 */
	@Test
	public void tests() {
		executor.execute("classpath:templating/mail_01.thtest");
		Assert.assertTrue(executor.isAllOK());
	}

}
