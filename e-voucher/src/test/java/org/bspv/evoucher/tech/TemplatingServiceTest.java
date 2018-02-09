package org.bspv.evoucher.tech;

import java.math.BigDecimal;
import java.util.Locale;
import java.util.UUID;

import org.bspv.evoucher.core.model.EVoucher;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
public class TemplatingServiceTest {

	@Autowired
	private TemplatingService templatingService;

	@Test
	public void buildEmailContentFromEVoucherTest() throws Exception {
		// given
		UUID uuid = UUID.fromString("08f75844-5c6b-4a5b-b239-801f6ba2661f");
		String name = "avereyl";
		String email = "mail@email.com";
		BigDecimal amount = new BigDecimal(10.00);
		
		// when
//		@formatter:off
		EVoucher eVoucher = EVoucher.builder()
				.withId(uuid)
				.withName(name)
				.withEmail(email)
				.withAmount(amount)
				.build();
//		@formatter:on
		String content = templatingService.buildEmailContentFromEVoucher(eVoucher);
		
		// then
		Assert.assertTrue("The name must be present in the text.", content.contains(name));
		Assert.assertTrue("The amount must be present in the text.", content.contains(TemplatingService.formatAmount(amount, Locale.FRANCE, 2)));
	}


}
