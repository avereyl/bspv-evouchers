/**
 * 
 */
package org.bspv.evoucher.rest.controller.exception;

import java.io.Serializable;

import org.springframework.http.HttpStatus;

import lombok.Builder;
import lombok.Getter;

/**
 *
 */
@Builder
public class ErrorResponse implements Serializable {

	/**
	 * Generated serial Version UID.
	 */
	private static final long serialVersionUID = 2398853792754213581L;
	
	/**
	 * HTTP status for this error.
	 */
	@Getter
	private final HttpStatus status;
	/**
	 * Error code for the API
	 */
	@Getter
	private final Integer code;
	/**
	 * URL responsible for the problem.
	 */
	@Getter
	private final String requestUrl;
	/**
	 * Message
	 */
	@Getter
	private final String message;
	/**
	 * URL toward more information
	 */
	@Getter
	private final String infoUrl;
	
}
