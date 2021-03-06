/**
 * 
 */
package org.bspv.evoucher.tech.helper;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Arrays;
import java.util.Locale;
import java.util.StringJoiner;
import java.util.stream.Collectors;

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
        return datetime == null ? ""
                : datetime.format(DateTimeFormatter.ofLocalizedDate(FormatStyle.LONG).withLocale(Locale.FRANCE));
    }

    public static String formatDateInFrench(LocalDateTime datetime) {
        return datetime == null ? ""
                : datetime.format(DateTimeFormatter.ofLocalizedDate(FormatStyle.SHORT).withLocale(Locale.FRANCE));
    }

    /**
     * Limit the given name so that it fits the report layout.
     * <ul>
     * <li>3 lines maximum
     * <li>45 characters per line
     * </ul>
     * 
     * @param name
     * @return
     */
    public static String formatName4print(String name) {
        return Arrays.asList(name.split("\n"))
                .stream()
                .limit(3)
                .map(s -> s.substring(0, Math.min(45, s.length())))
                .collect(Collectors.joining("\n"));
    }
}
