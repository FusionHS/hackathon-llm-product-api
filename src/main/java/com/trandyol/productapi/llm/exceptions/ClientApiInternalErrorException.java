package com.trandyol.productapi.llm.exceptions;

public class ClientApiInternalErrorException extends RuntimeException {

	public ClientApiInternalErrorException(String message) {
		super(message);
	}
}
