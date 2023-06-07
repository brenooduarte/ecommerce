package com.ecommerce.domain.exceptions;

public class UserAlreadyExistsException extends Exception {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public UserAlreadyExistsException() {

    }

    public UserAlreadyExistsException(String message) {
        super(message);
    }
}
