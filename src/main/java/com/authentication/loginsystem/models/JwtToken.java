package com.authentication.loginsystem.models;

import lombok.Getter;
@Getter
public class JwtToken {
	private final String jwtToken;

	public JwtToken(String jwtToken) {
		super();
		this.jwtToken = jwtToken;
	}
}
