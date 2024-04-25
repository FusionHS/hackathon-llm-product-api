package com.trandyol.productapi.llm.exceptions;

public class ClientResourceNotFoundErrorException extends RuntimeException {

	public static final String ERROR_MESSAGE_KEY = "client.resource.not.found";

	public ClientResourceNotFoundErrorException(String message) {
		super(message);
	}
}
