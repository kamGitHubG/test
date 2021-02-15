package com.nominet.exception;

/**
 * 
 * Encapsulate application errors into this class.
 *
 */
public class NominetRuntimeException extends RuntimeException {
	private ErrorCode errorCode;
	
	public NominetRuntimeException(ErrorCode errorCode, String message) {
		super(message);
		this.errorCode = errorCode;
	}

	public ErrorCode getErrorCode() {
		return errorCode;
	}
}
