package com.ecommerce.domain.exceptions;

public class CityNotFoundException extends EntityNotFoundException {

    private static final long serialVersionUID = 1L;

    public CityNotFoundException(String message) {
        super(message);
    }

    public CityNotFoundException(Long cidadeId) {
        this(String.format("There is no record of city with code %d", cidadeId));
    }
}
