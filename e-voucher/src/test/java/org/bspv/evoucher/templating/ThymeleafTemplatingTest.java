/**
 * 
 */
package org.bspv.evoucher.templating;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;

import org.junit.BeforeClass;
import org.junit.jupiter.api.Test;
import org.thymeleaf.dialect.IDialect;
import org.thymeleaf.spring5.dialect.SpringStandardDialect;
import org.thymeleaf.testing.templateengine.context.web.SpringWebProcessingContextBuilder;
import org.thymeleaf.testing.templateengine.engine.TestExecutor;

/**
 * @author BILLAUDG
 *
 */
public class ThymeleafTemplatingTest {
	
	private TestExecutor executor;
	
	@BeforeClass
	public void setup() {
		final List<IDialect> dialects = new ArrayList<IDialect>();
		dialects.add(new SpringStandardDialect());
		final SpringWebProcessingContextBuilder springPCBuilder = new SpringWebProcessingContextBuilder();
		springPCBuilder.setApplicationContextConfigLocation(null);
		executor = new TestExecutor();
		executor.setProcessingContextBuilder(springPCBuilder);
		executor.setDialects(dialects);
	}

	/**
	 * 
	 */
	@Test
	public void tests() {
		executor.execute("classpath:templating/mail_01.thtest");
		assertThat(executor.isAllOK()).isTrue();
	}

}
