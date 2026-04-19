package com.mitocode.library.application.exception;

public class BookAlreadyBorrowedException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public BookAlreadyBorrowedException(String message) {
		super(message);
	}

}
