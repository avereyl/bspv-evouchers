/**
 * 
 */
package org.bspv.evoucher.rest.controller.helper;


import static org.assertj.core.api.Assertions.assertThat;

import org.bspv.evoucher.rest.controller.exception.RangeUnsatisfiableException;
import org.springframework.data.domain.PageRequest;
import org.testng.annotations.Test;

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
		assertThat(pr).isNotNull();
		assertThat(pr.getPageSize()).isEqualTo(20-10);
		assertThat(pr.getPageNumber()).isEqualTo(1);
		assertThat(pr.getOffset()).isEqualTo(10);
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
		assertThat(pr).isNotNull();
        assertThat(pr.getPageSize()).isEqualTo(PaginationHelper.DEFAULT_PAGE_SIZE);
        assertThat(pr.getPageNumber()).isEqualTo(0);
        assertThat(pr.getOffset()).isEqualTo(0);
	}

	/**
	 * 
	 */
	@Test(expectedExceptions = RangeUnsatisfiableException.class)
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
	@Test(expectedExceptions = IllegalArgumentException.class)
	public void badFormatRangeToPageRequestTest() {
		// given
		String range = "a+10";
		// when
		PaginationHelper.rangeToPageRequest(range);
		// then ...
	}

}
