package com.ecommerce.domain.exceptions;

public class EntityNotFoundException extends RuntimeException{

    public static final long serialVersionUID = 1L;

    public EntityNotFoundException(String message) {
            super(message);
    }
}
