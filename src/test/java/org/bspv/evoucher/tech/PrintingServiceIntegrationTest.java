package org.bspv.evoucher.tech;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.fail;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.apache.pdfbox.preflight.PreflightDocument;
import org.apache.pdfbox.preflight.ValidationResult;
import org.apache.pdfbox.preflight.exception.SyntaxValidationException;
import org.apache.pdfbox.preflight.parser.PreflightParser;
import org.bspv.evoucher.core.model.EVoucher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import lombok.extern.slf4j.Slf4j;
import net.sf.jasperreports.engine.JRException;

/**
 * 
 *
 */
@Slf4j
@SpringBootTest
public class PrintingServiceIntegrationTest extends AbstractTestNGSpringContextTests {

	@Autowired
	@Qualifier("printingServiceJasper")
	private PrintingService printingService;
	
	@DataProvider(name="data")
	public Object[][] dataProvider() {
	    Object[][] data = {
	            {"Test company", new BigDecimal(137.271d), "target/eVoucher_test01.pdf"},
	            {"Lorem ipsum dolor sit amet\nLorem ipsum dolor sit amet\nLorem ipsum dolor sit amet\nLorem ipsum dolor sit amet", new BigDecimal(137.271d), "target/eVoucher_test02.pdf"},
	            {"Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur.", new BigDecimal(137.271d), "target/eVoucher_test03.pdf"}
	    };
	    return data;
	}

	@Test(priority=0, dataProvider="data")
	public void printingEVouchers(String donor, BigDecimal amount, String path) throws IOException, JRException {
		// given
		EVoucher evoucher = EVoucher.builder().withName(donor).withAmount(amount).requestDate(LocalDateTime.now()).build();
		// when
		ByteArrayOutputStream baos = printingService.printOriginalEVoucher(evoucher);
		OutputStream outputStream = new FileOutputStream(path);
		baos.writeTo(outputStream);
		baos.close();
		outputStream.close();
	}
	

	/**
	 * @see https://pdfbox.apache.org/1.8/cookbook/pdfavalidation.html
	 * @throws IOException
	 */
	@Test(dataProvider="data", dependsOnMethods="printingEVouchers")
	public void pdf1AValidation(String donor, BigDecimal amount, String path) throws IOException {
		ValidationResult result = null;
		PreflightParser parser = new PreflightParser(new File(path));
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
			assertThat(document.getNumberOfPages()).isEqualTo(1);
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
