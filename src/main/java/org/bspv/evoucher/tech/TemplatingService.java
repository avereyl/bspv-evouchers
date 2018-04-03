/**
 * 
 */
package org.bspv.evoucher.tech;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.NumberFormat;
import java.util.Locale;

import org.bspv.evoucher.core.model.EVoucher;

/**
 *
 */
public interface TemplatingService {

	/**
	 * 
	 * @param eVoucher
	 * @return
	 */
	String buildEmailContentFromEVoucher(EVoucher eVoucher);
	/**
	 * 
	 * @param amount
	 * @param locale
	 * @param nbOfDecimal
	 * @return
	 */
	public static String formatAmount(BigDecimal amount, Locale locale, int nbOfDecimal) {
		NumberFormat frenchCurrencyFormat = NumberFormat.getCurrencyInstance(locale);
		frenchCurrencyFormat.setMinimumFractionDigits(nbOfDecimal);
		frenchCurrencyFormat.setMaximumFractionDigits(nbOfDecimal);
		return frenchCurrencyFormat.format(amount.setScale(nbOfDecimal, RoundingMode.HALF_EVEN).doubleValue());
	}
	
	public static String formatDonorName(EVoucher eVoucher) {
	    String name = eVoucher.getName();
	    // in case of name on several lines then we keep the first line only.
	    if (name.indexOf('\n') != -1) {
	       name = name.substring(0, name.indexOf('\n'));
	    }
	    return name;
	}

}
