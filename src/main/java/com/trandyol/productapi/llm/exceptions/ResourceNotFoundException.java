package com.trandyol.productapi.llm.exceptions;

public class ResourceNotFoundException extends RuntimeException {

	public static final String ERROR_MESSAGE_KEY = "resource.not.found";

	public ResourceNotFoundException(String message) {
		super(message);
	}
}
