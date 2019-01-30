package com.codex.trial.trial.exception;

public class BookNotFoundException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public BookNotFoundException() {
		// TODO Auto-generated constructor stub
	}
	public BookNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}
}
