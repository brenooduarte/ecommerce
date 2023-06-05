package com.ecommerce.domain.exceptions;

public class UserAlreadyExistsException extends Exception {

    public UserAlreadyExistsException() {

    }

    public UserAlreadyExistsException(String message) {
        super(message);
    }
}
