package com.api.ecommerce.exceptions;

public class UserAlreadyExistsException extends Exception {

    public UserAlreadyExistsException() {

    }

    public UserAlreadyExistsException(String message) {
        super(message);
    }
}
