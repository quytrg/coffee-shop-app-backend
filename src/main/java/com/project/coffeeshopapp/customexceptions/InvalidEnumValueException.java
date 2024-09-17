package com.project.coffeeshopapp.customexceptions;

import lombok.Getter;

@Getter
public class InvalidEnumValueException extends RuntimeException {
    private final String fieldName;
    public InvalidEnumValueException(String fieldName, String message) {
        super(message);
        this.fieldName = fieldName;
    }
}
