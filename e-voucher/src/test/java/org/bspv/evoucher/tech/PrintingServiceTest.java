package org.bspv.evoucher.tech;

import static org.junit.Assert.fail;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.time.LocalDateTime;

import javax.annotation.PostConstruct;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.preflight.PreflightDocument;
import org.apache.pdfbox.preflight.ValidationResult;
import org.apache.pdfbox.preflight.exception.SyntaxValidationException;
import org.apache.pdfbox.preflight.parser.PreflightParser;
import org.apache.pdfbox.text.PDFTextStripper;
import org.bspv.evoucher.core.model.EVoucher;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import lombok.extern.slf4j.Slf4j;

/**
 * 
 *
 */
@Slf4j
@SpringBootTest
@RunWith(SpringRunner.class)
public class PrintingServiceTest {

	private static final String EVOUCHER_FILEPATH = "target/eVoucher_test.pdf";

	@Autowired
	@Qualifier("printingServiceJasper")
	private PrintingService printingService;

	@PostConstruct
	public void printingTestEVoucher() throws IOException {
		// given
		EVoucher evoucher = EVoucher.builder().withName("SARL Bonne nuit les petits loups")
				.withAmount(new BigDecimal(137.271d)).requestDate(LocalDateTime.now()).build();
		// when
		ByteArrayOutputStream baos = printingService.printOriginalEVoucher(evoucher);
		OutputStream outputStream = new FileOutputStream(EVOUCHER_FILEPATH);
		baos.writeTo(outputStream);
		baos.close();
		outputStream.close();
	}

	@Test
	public void printOriginalEVoucherTest() throws IOException {
		PDDocument document = null;
		try {

			document = PDDocument.load(new File(EVOUCHER_FILEPATH));
			PDFTextStripper stripper = new PDFTextStripper();
			String content = stripper.getText(document);

			// tests on content
			Assert.assertNotNull("The document content should not be null.", content);
			// Assert.assertTrue("", content.contains("Hello World"));

		} finally {
			if (document != null) {
				document.close();
			}
		}
	}

	/**
	 * @see https://pdfbox.apache.org/1.8/cookbook/pdfavalidation.html
	 * @throws IOException
	 */
	@Test
	public void pdf1AValidation() throws IOException {
		ValidationResult result = null;
		PreflightParser parser = new PreflightParser(new File(EVOUCHER_FILEPATH));
		try {

			/*
			 * Parse the PDF file with PreflightParser that inherits from the
			 * NonSequentialParser. Some additional controls are present to check a set of
			 * PDF/A requirements. (Stream length consistency, EOL after some Keyword...)
			 */
			parser.parse();

			/*
			 * Once the syntax validation is done, the parser can provide a
			 * PreflightDocument (that inherits from PDDocument) This document process the
			 * end of PDF/A validation.
			 */
			PreflightDocument document = parser.getPreflightDocument();
			document.validate();

			// Get validation result
			result = document.getResult();
			document.close();

		} catch (SyntaxValidationException e) {
			/*
			 * the parse method can throw a SyntaxValidationException if the PDF file can't
			 * be parsed. In this case, the exception contains an instance of
			 * ValidationResult
			 */
			result = e.getResult();
		}

		// display validation result
		if (result.isValid()) {
			log.debug("The file is a valid PDF/A-1b file");
		} else {
			log.error("The file is not valid, error(s) :");
			result.getErrorsList().stream()
					.forEach(error -> log.error("{0} : {1}", error.getErrorCode(), error.getDetails()));
			fail();
		}
	}
}
