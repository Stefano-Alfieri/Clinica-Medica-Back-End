package com.example.demo.response;

import com.example.demo.model.Token;

public class AuthResponse {

	private Token token;

	public AuthResponse(Token token) {
		this.token = token;
	}


	public Token getToken() {
		return token;
	}

	public void setToken(Token token) {
		this.token = token;
	}

}
