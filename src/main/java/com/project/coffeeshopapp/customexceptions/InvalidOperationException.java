package com.project.coffeeshopapp.customexceptions;

public class InvalidOperationException extends RuntimeException {
    public InvalidOperationException(String message) {
        super(message);
    }
}
