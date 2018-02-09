/**
 * 
 */
package org.bspv.evoucher.rest.controller.exception;

import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author guillaume
 *
 */
@ResponseStatus(org.springframework.http.HttpStatus.NOT_FOUND)
public class NotFoundException extends RuntimeException {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

}
