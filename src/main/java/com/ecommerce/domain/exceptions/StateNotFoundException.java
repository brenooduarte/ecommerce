package com.ecommerce.domain.exceptions;

public class StateNotFoundException extends EntityNotFoundException {

    private static final long serialVersionUID = 1L;

    public StateNotFoundException(String message) {
        super(message);
    }

    public StateNotFoundException(Long stateId) {
        this(String.format("There is no record of state with code %d", stateId));
    }
}
