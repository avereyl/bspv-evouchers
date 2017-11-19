/**
 * 
 */
package org.bspv.evoucher.tech.impl;

import java.io.ByteArrayOutputStream;
import java.util.Locale;

import org.bspv.evoucher.config.mail.MailConfig;
import org.bspv.evoucher.core.business.UserBusinessService;
import org.bspv.evoucher.core.model.EVoucher;
import org.bspv.evoucher.core.model.User;
import org.bspv.evoucher.tech.MailingService;
import org.bspv.evoucher.tech.TemplatingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

/**
 *
 */
@Slf4j
@Service
public class MailingServiceDefault implements MailingService {


	/**
	 * Java mail sender provided by spring framework.
	 */
	@Autowired
	private JavaMailSender javaMailSender;

	/**
	 * Templating service.
	 */
	@Autowired
	private TemplatingService templatingService;

	/**
	 * Service handling users.
	 */
	@Autowired
	private UserBusinessService userBusinessService;
	
	/**
	 * Message source @see {@link MailConfig}
	 */
	@Autowired
	@Qualifier("emailMessageSource")
    private MessageSource messageSource;
	
	/**
	 * 
	 */
	@Value("${mail.from}")
	private String mailFromAddress;
	/**
	 * 
	 */
	@Value("${mail.archive}")
	private String mailArchiveAddress;

	/*
	 * (non-Javadoc)
	 * @see org.bspv.evoucher.tech.MailingService#sendEVoucherPrint(org.bspv.evoucher.core.model.EVoucher, java.io.ByteArrayOutputStream)
	 */
	@Override
	public EVoucher sendEVoucherPrint(EVoucher eVoucher, final ByteArrayOutputStream baos) {

		MimeMessagePreparator messagePreparator = mimeMessage -> {
			MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, true);
			messageHelper.setFrom(this.mailFromAddress);
			messageHelper.setTo(eVoucher.getEmail());
			// add team members issuing the evoucher
			for (User user : userBusinessService.findMembers(eVoucher.getTeam())) {
				messageHelper.addBcc(user.getEmail());
			}
			// add archiving address
			
			messageHelper.addBcc(this.mailArchiveAddress);
			messageHelper.setSubject(messageSource.getMessage("mail.subject", new Object[] {}, Locale.getDefault()));
			// generate content																						
			String content = templatingService.buildEmailContentFromEVoucher(eVoucher);
			messageHelper.setText(content, true);
			// add attachement
			if (baos != null) {
				messageHelper.addAttachment(MailingService.computeFilename(eVoucher), new ByteArrayResource(baos.toByteArray()));
			}
		};
		try {
			javaMailSender.send(messagePreparator);
			log.info("eVoucher {} sent to {}", eVoucher.getId(), eVoucher.getEmail());
		} catch (MailException e) {
			log.error("Exception while sending email for eVoucher {} !", eVoucher.getId());
			log.error(e.toString());
		}

		return eVoucher;
	}

}
