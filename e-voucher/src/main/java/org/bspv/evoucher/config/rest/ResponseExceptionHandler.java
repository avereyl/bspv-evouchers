/**
 * 
 */
package org.bspv.evoucher.config.rest;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.UnsatisfiedServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.support.DefaultHandlerExceptionResolver;

/**
 *
 */
//@ControllerAdvice(basePackageClasses = ControllerRootScan.class)
public class ResponseExceptionHandler extends DefaultHandlerExceptionResolver {

	@ExceptionHandler(OptimisticLockingFailureException.class)
	void handleOptimisticLockingFailureException(HttpServletResponse response, OptimisticLockingFailureException ex) throws IOException {
		response.sendError(HttpStatus.CONFLICT.value());
	}
	
	@ExceptionHandler
	void handleIllegalArgumentException(HttpServletResponse response, IllegalArgumentException e) throws IOException {
	    response.sendError(HttpStatus.BAD_REQUEST.value());
	}
	
	@ExceptionHandler({UnsatisfiedServletRequestParameterException.class})
	void handleUnsatisfiedServletRequestParameterException(HttpServletResponse response, UnsatisfiedServletRequestParameterException ex) throws IOException {
		response.sendError(HttpStatus.BAD_REQUEST.value());
	}
	
	@ExceptionHandler({NoHandlerFoundException.class})
	void handleNoHandlerFoundException(HttpServletResponse response, NoHandlerFoundException ex) throws IOException {
		response.sendError(HttpStatus.BAD_REQUEST.value());
	}

	

	
}
