package com.nominet.exception;

/**
 * DTO object that will be used to return error information to api consumer in event of errors and exceptions.
 */

import java.time.LocalDateTime;

import lombok.Data;


@Data
public class NominetErrorReport {
	private String errorMessage;
	private LocalDateTime localDateTime;

	public NominetErrorReport(String message) {
		this.errorMessage = message;
		this.localDateTime = LocalDateTime.now();
	}

}
