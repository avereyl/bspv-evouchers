/**
 * 
 */
package org.bspv.evoucher.tech.helper;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Locale;
import java.util.StringJoiner;

import com.ibm.icu.text.NumberFormat;
import com.ibm.icu.text.RuleBasedNumberFormat;
import com.ibm.icu.util.ULocale;

/**
 *
 */
public class PrintingHelper {

	/**
	 * 
	 */
	private PrintingHelper() {
	}

	/**
	 * 
	 * @param pt
	 * @return
	 */
	public static float pt2mm(float pt) {
		return pt * 25.4f / 72;
	}

	/**
	 * 
	 * @param mm
	 * @return
	 */
	public static float mm2pt(float mm) {
		return mm / 25.4f * 72;
	}

	public static String formatAmountInEurosAndLitteralFrench(float amount) {
		double euros = Math.floor(amount);
		double cents = Math.round((amount - Math.floor(amount)) * 100);
		NumberFormat formatter = new RuleBasedNumberFormat(ULocale.FRANCE, RuleBasedNumberFormat.SPELLOUT);
		StringJoiner sj = new StringJoiner(" ");
		sj.add(formatter.format(euros));
		if (cents > 0) {
			sj.add("euros,");
			sj.add(formatter.format(cents));
			sj.add("cents");
		} else {
			sj.add("euros");
		}
		return sj.toString();
	}

	public static String formatAmountInEurosAndFrench(float amount) {
		StringBuilder sb = new StringBuilder();
		NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(ULocale.FRANCE);
		currencyFormatter.setMaximumFractionDigits(2);
		currencyFormatter.setMinimumFractionDigits(2);
		sb.append(currencyFormatter.format(amount));
		return sb.toString();
	}

	public static String formatDateInLitteralFrench(LocalDateTime datetime) {
		return datetime == null ? "" : datetime.format(DateTimeFormatter.ofLocalizedDate(FormatStyle.LONG).withLocale(Locale.FRANCE));
	}
	public static String formatDateInFrench(LocalDateTime datetime) {
		return datetime == null ? "" : datetime.format(DateTimeFormatter.ofLocalizedDate(FormatStyle.SHORT).withLocale(Locale.FRANCE));
	}
}
