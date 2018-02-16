package org.bspv.evoucher.tech;

import static org.assertj.core.api.Assertions.assertThat;
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
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.MessageSource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.icegreen.greenmail.util.GreenMail;
import com.icegreen.greenmail.util.ServerSetup;

@SpringBootTest
public class MailingServiceIntegrationTest extends AbstractTestNGSpringContextTests {

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
	@BeforeClass
	public void setUp() throws Exception {
	    
	    MockitoAnnotations.initMocks(this);
	    
		smtpServer = new GreenMail(new ServerSetup(2525, null, "smtp"));
		smtpServer.start();
		((JavaMailSenderImpl) javaMailSender).setHost(smtpServer.getSmtp().getServerSetup().getBindAddress());
		((JavaMailSenderImpl) javaMailSender).setPort(smtpServer.getSmtp().getServerSetup().getPort());
	}

	/**
	 * Destruction of the fake SMTP server.
	 * @throws Exception
	 */
	@AfterClass
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
		// eVoucher email + archive recipients (+no team = 0 other recipients)-> 2 recipient
		assertThat(smtpServer.getReceivedMessages().length).isEqualTo(2);
	}

	
	private void assertReceivedMessageSubjectIs(String expectedSubject) throws IOException, MessagingException {
		MimeMessage[] receivedMessages = smtpServer.getReceivedMessages();
		assertThat(receivedMessages.length).isGreaterThan(0);
		String receivedSubject = receivedMessages[0].getSubject();
		assertThat(receivedSubject).isEqualTo(expectedSubject);
	}

}
