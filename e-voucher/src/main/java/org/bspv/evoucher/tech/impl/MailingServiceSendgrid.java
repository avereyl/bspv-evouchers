/**
 * 
 */
package org.bspv.evoucher.tech.impl;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Arrays;
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
import org.springframework.context.annotation.Profile;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.codec.Base64;
import org.springframework.stereotype.Service;

import com.sendgrid.Attachments;
import com.sendgrid.Content;
import com.sendgrid.Email;
import com.sendgrid.Mail;
import com.sendgrid.Method;
import com.sendgrid.Personalization;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.SendGrid;

import lombok.extern.slf4j.Slf4j;

/**
 *
 */
@Slf4j
@Service
@Profile("heroku")
public class MailingServiceSendgrid implements MailingService {

	@Autowired
	private SendGrid sendgrid;

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
	 * 
	 * @see org.bspv.evoucher.tech.MailingService#sendEVoucherPrint(org.bspv.evoucher.core.model.EVoucher,
	 * java.io.ByteArrayOutputStream)
	 */
	@Override
	public EVoucher sendEVoucherPrint(final EVoucher eVoucher, ByteArrayOutputStream baos) {

		Mail mail = new Mail();
		mail.setFrom(new Email(this.mailFromAddress));
		mail.setSubject(messageSource.getMessage("mail.subject", new Object[] {}, Locale.getDefault()));

		Personalization personalization = new Personalization();
		mail.addPersonalization(personalization);
		personalization.addTo(new Email(eVoucher.getEmail()));
		
		// add team members issuing the evoucher
		for (User user : userBusinessService.findMembers(eVoucher.getTeam())) {
			personalization.addBcc(new Email(user.getEmail()));
		}
		// add archiving address
		personalization.addBcc(new Email(this.mailArchiveAddress));
		mail.addContent(new Content(MediaType.TEXT_HTML.getType(), templatingService.buildEmailContentFromEVoucher(eVoucher)));
		// add attachment
		Attachments eVoucherPrintAttachement = new Attachments();
		eVoucherPrintAttachement.setContent(Arrays.toString(Base64.encode(baos.toByteArray())));
		eVoucherPrintAttachement.setFilename(MailingService.computeFilename(eVoucher));
		eVoucherPrintAttachement.setType(MediaType.APPLICATION_PDF.getType());
		mail.addAttachments(eVoucherPrintAttachement);

		Request request = new Request();
		try {
			request.setMethod(Method.POST);
			request.setEndpoint("mail/send");
			request.setBody(mail.build());

			Response response = sendgrid.api(request);
			log.info("Mail sent for evoucher {}, with status {}", eVoucher.getId(), response.getStatusCode());
		} catch (IOException ex) {
			log.error("Unable to send the mail with Sendgrid.", ex);
		}

		return eVoucher;
	}

}
