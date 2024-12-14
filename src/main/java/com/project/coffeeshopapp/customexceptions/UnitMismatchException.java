package com.project.coffeeshopapp.customexceptions;

public class UnitMismatchException extends RuntimeException {
    public UnitMismatchException(String message) {
        super(message);
    }
}
