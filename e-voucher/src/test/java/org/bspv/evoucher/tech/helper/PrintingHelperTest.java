package org.bspv.evoucher.tech.helper;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.junit.Assert;
import org.junit.Test;

public class PrintingHelperTest {

	/**
	 *
	 */
	@Test
	public void ormatAmountInEurosAndLitteralFrenchTest() {
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
		Assert.assertEquals("Texts should be equals", "cinquante euros", formattedAmountWithoutCents);
		Assert.assertEquals("Texts should be equals", "cinquante euros, trente-deux cents", formattedAmountWithCents);
		Assert.assertEquals("Texts should be equals", "cinquante euros, trente-et-un cents",
				formattedAmountWithTooManyCents);
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
		Assert.assertEquals("Texts should be equals", "50,00 €", formattedAmountWithoutCents);
		Assert.assertEquals("Texts should be equals", "50,32 €", formattedAmountWithCents);
		Assert.assertEquals("Texts should be equals", "50,31 €", formattedAmountWithTooManyCents);
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
		Assert.assertEquals("Texts should be equals", "", formattedNullDate);
		Assert.assertEquals("Texts should be equals", "17 octobre 2017", formattedDate);
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
		Assert.assertEquals("Texts should be equals", "", formattedNullDate);
		Assert.assertEquals("Texts should be equals", "17/10/17", formattedDate);
	}

}
