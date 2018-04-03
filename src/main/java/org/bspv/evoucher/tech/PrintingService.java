/**
 * 
 */
package org.bspv.evoucher.tech;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import org.bspv.evoucher.core.model.EVoucher;

import net.sf.jasperreports.engine.JRException;

/**
 *
 */
public interface PrintingService {
	
	ByteArrayOutputStream printOriginalEVoucher(EVoucher evoucher) throws IOException, JRException;
	
	ByteArrayOutputStream printDuplicataEVoucher(EVoucher evoucher) throws IOException, JRException;
}
