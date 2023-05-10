package org.bspv.evoucher.tech.helper;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.junit.jupiter.api.Test;

public class PrintingHelperTest {

	/**
	 *
	 */
	@Test
	public void formatAmountInEurosAndLitteralFrenchTest() {
		// given
		float amountWithoutCents = 50.0f;
		float amountWithCents = 50.32f;
		float amountWithTooManyCents = 50.31415f;
		// when
		String formattedAmountWithoutCents = PrintingHelper.formatAmountInEurosAndLitteralFrench(amountWithoutCents);
		String formattedAmountWithCents = PrintingHelper.formatAmountInEurosAndLitteralFrench(amountWithCents);
		String formattedAmountWithTooManyCents = PrintingHelper
				.formatAmountInEurosAndLitteralFrench(amountWithTooManyCents);
		// then
		assertThat(formattedAmountWithoutCents).isEqualTo("cinquante euros");
		assertThat(formattedAmountWithCents).isEqualTo("cinquante euros, trente-deux cents");
		assertThat(formattedAmountWithTooManyCents).isEqualTo("cinquante euros, trente-et-un cents");
	}

	/**
	 *
	 */
	@Test
	public void formatAmountInEurosAndFrenchTest() {
		// given
		float amountWithoutCents = 50.0f;
		float amountWithCents = 50.32f;
		float amountWithTooManyCents = 50.31415f;
		// when
		String formattedAmountWithoutCents = PrintingHelper.formatAmountInEurosAndFrench(amountWithoutCents);
		String formattedAmountWithCents = PrintingHelper.formatAmountInEurosAndFrench(amountWithCents);
		String formattedAmountWithTooManyCents = PrintingHelper.formatAmountInEurosAndFrench(amountWithTooManyCents);
		// then
		assertThat(formattedAmountWithoutCents).isEqualTo("50,00 €");
        assertThat(formattedAmountWithCents).isEqualTo("50,32 €");
        assertThat(formattedAmountWithTooManyCents).isEqualTo("50,31 €");
	}

	/**
	 *
	 */
	@Test
	public void formatDateInLitteralFrench() {
		// given
		LocalDateTime aNullDate = null;
		LocalDateTime aDate =  LocalDateTime.of(LocalDate.parse("20171017", DateTimeFormatter.BASIC_ISO_DATE), LocalDateTime.MIN.toLocalTime());
		// when
		String formattedNullDate = PrintingHelper.formatDateInLitteralFrench(aNullDate);
		String formattedDate = PrintingHelper.formatDateInLitteralFrench(aDate);
		// then
		assertThat(formattedNullDate).isEqualTo("");
		assertThat(formattedDate).isEqualTo("17 octobre 2017");
	}

	/**
	 *
	 */
	@Test
	public void formatDateInFrench() {
		// given
		LocalDateTime aNullDate = null;
		LocalDateTime aDate =  LocalDateTime.of(LocalDate.parse("20171017", DateTimeFormatter.BASIC_ISO_DATE), LocalDateTime.MIN.toLocalTime());
		// when
		String formattedNullDate = PrintingHelper.formatDateInFrench(aNullDate);
		String formattedDate = PrintingHelper.formatDateInFrench(aDate);
		// then
		assertThat(formattedNullDate).isEqualTo("");
        assertThat(formattedDate).isEqualTo("17/10/2017");
	}

}
