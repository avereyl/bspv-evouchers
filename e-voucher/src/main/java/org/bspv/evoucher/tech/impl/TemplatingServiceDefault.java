/**
 * 
 */
package org.bspv.evoucher.tech.impl;

import java.util.Locale;

import org.bspv.evoucher.core.model.EVoucher;
import org.bspv.evoucher.tech.TemplatingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

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
    @Qualifier("emailMessageSource")
    private MessageSource messageSource;

	/**
	 * 
	 */
	@Autowired
	private SpringTemplateEngine templateEngine;

	/*
	 * (non-Javadoc)
	 * @see org.bspv.evoucher.tech.TemplatingService#buildEmailContentFromEVoucher(org.bspv.evoucher.core.model.EVoucher)
	 */
	@Override
	public String buildEmailContentFromEVoucher(EVoucher eVoucher) {
	    
		Context thymeleafContext = new Context(Locale.FRANCE);
		String eVoucherReference = eVoucher.getId().toString();
        eVoucherReference = eVoucherReference.substring(eVoucherReference.length()-12);
		thymeleafContext.setVariable("eVoucherReference", eVoucherReference);
		thymeleafContext.setVariable("donorName", TemplatingService.formatDonorName(eVoucher));
		thymeleafContext.setVariable("formattedAmount", TemplatingService.formatAmount(eVoucher.getAmount(), Locale.FRANCE, 2));
		templateEngine.setTemplateEngineMessageSource(messageSource);
		return templateEngine.process(EVOUCHER_MAIL_TEMPLATE_KEY, thymeleafContext);
	}

}
