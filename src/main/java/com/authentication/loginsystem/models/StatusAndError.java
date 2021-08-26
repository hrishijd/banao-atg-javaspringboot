package com.authentication.loginsystem.models;

import lombok.Data;

@Data
public class StatusAndError {
	public StatusAndError(String status, String message) {
		super();
		this.status = status;
		this.message = message;
	}
	private String status;
	private String message;
}
