/**
 * 
 */
package org.bspv.evoucher.tech.impl;

import java.util.Locale;

import org.bspv.evoucher.core.model.EVoucher;
import org.bspv.evoucher.tech.TemplatingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

/**
 *
 */
@Service
public class TemplatingServiceDefault implements TemplatingService {


	/**
	 * 
	 */
	private static final String EVOUCHER_MAIL_TEMPLATE_KEY = "eVoucherMailTemplate";

	/**
	 * 
	 */
	@Autowired
	private TemplateEngine templateEngine;

	/*
	 * (non-Javadoc)
	 * @see org.bspv.evoucher.tech.TemplatingService#buildEmailContentFromEVoucher(org.bspv.evoucher.core.model.EVoucher)
	 */
	@Override
	public String buildEmailContentFromEVoucher(EVoucher eVoucher) {
		Context thymeleafContext = new Context();
		thymeleafContext.setVariable("eVoucher", eVoucher);
		thymeleafContext.setVariable("formattedAmount",
				TemplatingService.formatAmount(eVoucher.getAmount(), Locale.FRANCE, 2));
		return templateEngine.process(EVOUCHER_MAIL_TEMPLATE_KEY, thymeleafContext);
	}

}
