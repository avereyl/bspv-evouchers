/**
 * 
 */
package org.bspv.evoucher.rest.controller.helper;

import org.bspv.evoucher.rest.controller.exception.RangeUnsatisfiableException;
import org.springframework.data.domain.PageRequest;
import org.springframework.util.StringUtils;

import lombok.extern.slf4j.Slf4j;

/**
 *
 */
@Slf4j
public final class PaginationHelper {
	
	/**
	 * 
	 */
	public static final String DEFAULT_PAGINATION_RANGE = "0-10";
	
	/**
	 * 
	 */
	public static final int DEFAULT_PAGE_SIZE = 10;
	/**
	 * 
	 */
	public static final int MAX_RANGE = 50;

	/**
	 * 
	 */
	private PaginationHelper() {}
	
	/**
	 * Return a page request from a range expressed as <start>-<end>
	 * 
	 * @param range
	 *            The range to transform.
	 * @return a {@link PageRequest}
	 */
	public static PageRequest rangeToPageRequest(String range) throws IllegalArgumentException {
		range = StringUtils.isEmpty(range) ? DEFAULT_PAGINATION_RANGE : range;
		PageRequest pr = new PageRequest(0, DEFAULT_PAGE_SIZE);
		String[] data = range.split("-");
		try {
			int start = Integer.parseInt(data[0]);
			int end = Integer.parseInt(data[1]);
			if (end - start <= 0 || end - start > MAX_RANGE) {
				throw new RangeUnsatisfiableException(
						"Range " + range + " not satisfiable. (Maximum size is " + MAX_RANGE + ")");
			}
			pr = new PageRequest(start / (end - start), end - start);
		} catch (NumberFormatException | IndexOutOfBoundsException ex) {
			log.error("Invalid range format");
			throw new IllegalArgumentException("Exception reading the range parameter", ex);
		}
		return pr;
	}

}
