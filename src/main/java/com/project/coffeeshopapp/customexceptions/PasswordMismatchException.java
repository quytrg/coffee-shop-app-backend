package com.project.coffeeshopapp.customexceptions;

public class PasswordMismatchException extends RuntimeException {
    public PasswordMismatchException() {
        super("Password mismatch");
    }
}
