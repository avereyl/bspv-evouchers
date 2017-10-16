/**
 * 
 */
package org.bspv.evoucher.tech;

import java.io.ByteArrayOutputStream;

import org.bspv.evoucher.core.model.EVoucher;

/**
 *
 */
public interface MailingService {

	/**
	 * Send to Contributor and to archiving address.
	 * 
	 * @param eVoucher
	 * @param baos
	 * @return
	 */
	EVoucher sendEVoucherPrint(EVoucher eVoucher, ByteArrayOutputStream baos);

	static String computeFilename(EVoucher eVoucher) {
		StringBuilder sb = new StringBuilder();
		sb.append(eVoucher.getId().toString());
		sb.append(".pdf");
		return sb.toString();
	}
}
