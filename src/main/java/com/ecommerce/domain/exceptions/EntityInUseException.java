package com.ecommerce.domain.exceptions;

import java.io.Serial;

public class EntityInUseException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 1L;

    public EntityInUseException(String message) {
        super(message);
    }

}
