package com.nominet.exception;

/**
 * Registering the exception handler with Spring context that will handle application exceptions and encapsulates the 
 * information into NominetErrorReport object.  This also sets the HTTP status code to the response object.
 */

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class NominetExceptionHandler {

	@ExceptionHandler(NominetRuntimeException.class)
	protected ResponseEntity<NominetErrorReport> handleNominetRuntimeException(
			NominetRuntimeException ex) {
		NominetErrorReport nominetErrorReport = new NominetErrorReport(ex.getMessage());
		return ResponseEntity.status(ex.getErrorCode().getHttpStatusCode())
				.body(nominetErrorReport);
	}

}