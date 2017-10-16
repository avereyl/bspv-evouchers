/**
 * 
 */
package org.bspv.evoucher.tech;

import java.awt.image.BufferedImage;

import org.bspv.evoucher.core.model.EVoucher;

/**
 *
 */
public interface BarcodeGenerationService {

	/**
	 * Generate the barcode for the given e-voucher.
	 * @param eVoucher The voucher
	 * @return The barcode as a {@link BufferedImage}
	 */
	BufferedImage generateBarcode(EVoucher eVoucher);

}
