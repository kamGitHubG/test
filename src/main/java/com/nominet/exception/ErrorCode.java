package com.nominet.exception;

/**
 * 
 * Enum to encapsulate type of application errors that could be thrown. Errors
 * are mapped to Http Status codes. This is the http code that will be returned
 * should the application throw this error.
 *
 */
public enum ErrorCode {
	
	NOT_FOUND(404);
	
	private final int httpStatusCode;
	
	private ErrorCode(int httpStatusCode) {
		this.httpStatusCode = httpStatusCode;
	}

	public int getHttpStatusCode() {
		return this.httpStatusCode;
	}
}
