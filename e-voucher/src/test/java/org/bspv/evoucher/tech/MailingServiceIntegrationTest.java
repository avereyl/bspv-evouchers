package org.bspv.evoucher.tech;

import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Locale;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.bspv.evoucher.config.mail.MailConfig;
import org.bspv.evoucher.core.business.UserBusinessService;
import org.bspv.evoucher.core.model.EVoucher;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.MessageSource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.test.context.junit4.SpringRunner;

import com.icegreen.greenmail.util.GreenMail;
import com.icegreen.greenmail.util.ServerSetup;

@SpringBootTest
@RunWith(SpringRunner.class)
public class MailingServiceIntegrationTest {

	/**
	 * Fake SMTP server for testing purpose.
	 */
	private GreenMail smtpServer;

	/**
	 * Mail sender for the fake SMTP server.
	 */
	@Autowired
	private JavaMailSender javaMailSender;
	
	/**
	 * Message source @see {@link MailConfig}
	 */
	@Autowired
	@Qualifier("emailMessageSource")
    private MessageSource messageSource;

	/**
	 * Mailing service to test with injected mocks.
	 */
	@Autowired
	@InjectMocks
	private MailingService mailingService;

	/**
	 * Mock of the {@link UserBusinessService} used in the {@link MailingService}
	 */
	@Mock
	private UserBusinessService userBusinessService;
	/**
	 * Mock of the {@link TemplatingService} used in the {@link MailingService}
	 */
	@Mock
	private TemplatingService templatingService;


	/**
	 * Creation of the fake SMTP server.
	 * @throws Exception
	 */
	@Before
	public void setUp() throws Exception {
		smtpServer = new GreenMail(new ServerSetup(2525, null, "smtp"));
		smtpServer.start();
		((JavaMailSenderImpl) javaMailSender).setHost(smtpServer.getSmtp().getServerSetup().getBindAddress());
		((JavaMailSenderImpl) javaMailSender).setPort(smtpServer.getSmtp().getServerSetup().getPort());
	}

	/**
	 * Destruction of the fake SMTP server.
	 * @throws Exception
	 */
	@After
	public void tearDown() throws Exception {
		smtpServer.stop();
	}

	/**
	 * 
	 * @throws Exception
	 */
	@Test
	public void sendEVoucherPrintTest() throws Exception {
		// given
		String subject = messageSource.getMessage("mail.subject", new Object[] {}, Locale.getDefault());
		ByteArrayOutputStream baos = null;
		EVoucher eVoucher = EVoucher.builder().withName("test").withAmount(new BigDecimal(10)).withEmail("avereyl@mail.com").build();
		

		// when
		when(userBusinessService.findMembers(eVoucher.getTeam())).thenReturn(new HashSet<>());
		when(templatingService.buildEmailContentFromEVoucher(eVoucher)).thenReturn("TEST");
		mailingService.sendEVoucherPrint(eVoucher, baos);

		// then
		// assert subject
		assertReceivedMessageSubjectIs(subject);
		// eVoucher email + archive recipients (+no team = 0 other recipients)-> 2 recipients
		assertEquals(2, smtpServer.getReceivedMessages().length);
	}

	
	private void assertReceivedMessageSubjectIs(String expectedSubject) throws IOException, MessagingException {
		MimeMessage[] receivedMessages = smtpServer.getReceivedMessages();
		assertThat("At least one message should have been sent.", receivedMessages.length, not(0));
		String receivedSubject = receivedMessages[0].getSubject();
		assertEquals("Subject of the mail is not the expected one.", expectedSubject, receivedSubject);
	}

}
