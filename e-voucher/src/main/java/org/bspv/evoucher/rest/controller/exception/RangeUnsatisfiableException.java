/**
 * 
 */
package org.bspv.evoucher.rest.controller.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 *
 */
@ResponseStatus(code=HttpStatus.REQUESTED_RANGE_NOT_SATISFIABLE, reason="The range you requested is not satisfiable. Please, reduce it.")
public class RangeUnsatisfiableException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1588966770796477250L;

	/**
	 * 
	 */
	public RangeUnsatisfiableException() {
		super();
	}

	/**
	 * @param message
	 * @param cause
	 */
	public RangeUnsatisfiableException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * @param message
	 */
	public RangeUnsatisfiableException(String message) {
		super(message);
	}

	/**
	 * @param cause
	 */
	public RangeUnsatisfiableException(Throwable cause) {
		super(cause);
	}



}
