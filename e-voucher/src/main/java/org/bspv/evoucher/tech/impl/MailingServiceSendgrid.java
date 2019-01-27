/**
 * 
 */
package org.bspv.evoucher.tech.impl;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
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
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

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
@Primary
@ConditionalOnProperty(value="mail.service.provider", havingValue="sendgrid")
public class MailingServiceSendgrid implements MailingService {


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
	
	@Value("${mail.sendgrid.apikey}")
	private String sendgridApiKey;

	/*
	 * (non-Javadoc)
	 * @see org.bspv.evoucher.tech.MailingService#sendEVoucherPrint(org.bspv.evoucher.core.model.EVoucher, java.io.ByteArrayOutputStream)
	 */
	@Override
	public EVoucher sendEVoucherPrint(EVoucher eVoucher, final ByteArrayOutputStream baos) {
		
		
		Email from = new Email(this.mailFromAddress);
		String subject = messageSource.getMessage("mail.subject", new Object[] {}, Locale.getDefault());
		
		Email to = null;
		String donorEmail = eVoucher.getEmail();
		if (!StringUtils.isEmpty(donorEmail)) {
			to = new Email(donorEmail);
		} else {
		    log.warn("No donor email: fallback to archive email.");
		    to = new Email(this.mailArchiveAddress);
		}
		try {
		
			Content content = new Content("text/html", templatingService.buildEmailContentFromEVoucher(eVoucher));
			
			Mail mail = new Mail(from, subject, to, content);
			Personalization personalization = new Personalization();
			personalization.addTo(to);
			personalization.setSubject(subject);
			// add team members issuing the evoucher
			for (User user : userBusinessService.findMembers(eVoucher.getTeam())) {
				personalization.addBcc(new Email(user.getEmail()));
			}
			// add archiving address
			personalization.addBcc(new Email(this.mailArchiveAddress));
			mail.addPersonalization(personalization);
			// add attachement
			if (baos != null) {
				Attachments.Builder builder = new Attachments.Builder(MailingService.computeFilename(eVoucher), new ByteArrayResource(baos.toByteArray()).getInputStream() );
				builder.withType("application/pdf");
//				builder.withDisposition("inline");
				mail.addAttachments(builder.build());
			} else {
				log.error("No attachment for this mail !");
			}

			SendGrid sg = new SendGrid(sendgridApiKey);

			Request request = new Request();
			request.setMethod(Method.POST);
			request.setEndpoint("mail/send");
			request.setBody(mail.build());
			Response response = sg.api(request);
			log.warn("Sendgrid API return code {}", response.getStatusCode());
			log.warn("Sendgrid response body {}", response.getBody());
			log.warn("Sendgrid response headers {}", response.getHeaders());
		} catch (IOException ex) {
			log.error("Failing to send mail via Sendgrid API !", ex);
		}

		return eVoucher;
	}

}
