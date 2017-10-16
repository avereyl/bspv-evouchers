/**
 * 
 */
package org.bspv.evoucher.tech;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import org.bspv.evoucher.core.model.EVoucher;

/**
 *
 */
public interface PrintingService {
	
	ByteArrayOutputStream printOriginalEVoucher(EVoucher evoucher) throws IOException;
	
	ByteArrayOutputStream printDuplicataEVoucher(EVoucher evoucher) throws IOException;
}
