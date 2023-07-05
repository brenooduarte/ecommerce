package com.ecommerce.domain.exceptions;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class ValidationError extends StandardError {

    List<FieldMessage> standardErrors = new ArrayList<>();

    public void addError(String fieldName, String message) {
        standardErrors.add(new FieldMessage(fieldName, message));
    }

}
