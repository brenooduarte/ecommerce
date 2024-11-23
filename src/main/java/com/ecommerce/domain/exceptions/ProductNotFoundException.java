package com.ecommerce.domain.exceptions;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.http.HttpStatus;

@EqualsAndHashCode(callSuper = true)
@Data
public class ProductNotFoundException extends RuntimeException {

    private ExceptionResponse exceptionResponse;

    public ProductNotFoundException() {
        super("No product found");

        this.exceptionResponse =
                new ExceptionResponse(
                        HttpStatus.NOT_FOUND.value(),
                        "No product found",
                        ""
                );
    }

}

