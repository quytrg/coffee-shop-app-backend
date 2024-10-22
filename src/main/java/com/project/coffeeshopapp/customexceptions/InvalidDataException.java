package com.project.coffeeshopapp.customexceptions;

import lombok.Getter;

@Getter
public class InvalidDataException extends RuntimeException {
    private final String field;
    public InvalidDataException(String field, String message) {
        super(message);
        this.field = field;
    }
}
