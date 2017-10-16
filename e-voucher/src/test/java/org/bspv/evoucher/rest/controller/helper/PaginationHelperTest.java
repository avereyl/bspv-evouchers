/**
 * 
 */
package org.bspv.evoucher.rest.controller.helper;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.bspv.evoucher.rest.controller.exception.RangeUnsatisfiableException;
import org.junit.Test;
import org.springframework.data.domain.PageRequest;

/**
 *
 */
public class PaginationHelperTest {

	/**
	 * 
	 */
	@Test
	public void rangeToPageRequestNominalTest() {
		// given
		String range = "10-20";
		// when
		PageRequest pr = PaginationHelper.rangeToPageRequest(range);
		// then
		assertNotNull("", pr);
		assertEquals("", 20 - 10, pr.getPageSize());
		assertEquals("", 1, pr.getPageNumber());
		assertEquals("", 10, pr.getOffset());
	}

	/**
	 * 
	 */
	@Test
	public void emptyRangeToPageRequestTest() {
		// given
		String range = "";
		// when
		PageRequest pr = PaginationHelper.rangeToPageRequest(range);
		// then
		assertNotNull("", pr);
		assertEquals("", PaginationHelper.DEFAULT_PAGE_SIZE, pr.getPageSize());
		assertEquals("", 0, pr.getPageNumber());
		assertEquals("", 0, pr.getOffset());
	}

	/**
	 * 
	 */
	@Test(expected = RangeUnsatisfiableException.class)
	public void unsatisfiableRangeToPageRequestTest() {
		// given
		String range = "0-300";
		// when
		PaginationHelper.rangeToPageRequest(range);
		// then ...
	}

	/**
	 * 
	 */
	@Test(expected = IllegalArgumentException.class)
	public void badFormatRangeToPageRequestTest() {
		// given
		String range = "a+10";
		// when
		PaginationHelper.rangeToPageRequest(range);
		// then ...
	}

}
