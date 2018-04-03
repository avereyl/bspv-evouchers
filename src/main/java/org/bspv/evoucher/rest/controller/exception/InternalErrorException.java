/**
 * 
 */
package org.bspv.evoucher.rest.controller.exception;

import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author guillaume
 *
 */
@ResponseStatus(org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR)
public class InternalErrorException extends RuntimeException {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

}
