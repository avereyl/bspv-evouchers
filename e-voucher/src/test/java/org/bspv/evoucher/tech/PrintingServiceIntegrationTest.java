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
import java.util.stream.Stream;

import org.apache.pdfbox.preflight.PreflightDocument;
import org.apache.pdfbox.preflight.ValidationResult;
import org.apache.pdfbox.preflight.exception.SyntaxValidationException;
import org.apache.pdfbox.preflight.parser.PreflightParser;
import org.bspv.evoucher.core.model.EVoucher;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;

import lombok.extern.slf4j.Slf4j;
import net.sf.jasperreports.engine.JRException;

/**
 * 
 *
 */
@Slf4j
@TestMethodOrder(OrderAnnotation.class)
@SpringBootTest
public class PrintingServiceIntegrationTest {

	@Autowired
	@Qualifier("printingServiceJasper")
	private PrintingService printingService;
	
	private static Stream<Arguments> provideEVouchersData() {
	    return Stream.of(
	      Arguments.of("Test company", new BigDecimal(137.271d), "target/eVoucher_test01.pdf"),
	      Arguments.of("Lorem ipsum dolor sit amet\nLorem ipsum dolor sit amet\nLorem ipsum dolor sit amet\nLorem ipsum dolor sit amet", new BigDecimal(137.271d), "target/eVoucher_test02.pdf"),
	      Arguments.of("Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur.", new BigDecimal(137.271d), "target/eVoucher_test03.pdf")
	    );
	}

	@Order(1)
	@ParameterizedTest
	@MethodSource("provideEVouchersData")
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
	@Order(2)
	@ParameterizedTest
	@MethodSource("provideEVouchersData")
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
