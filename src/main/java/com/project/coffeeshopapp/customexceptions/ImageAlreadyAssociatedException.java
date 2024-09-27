package com.project.coffeeshopapp.customexceptions;

public class ImageAlreadyAssociatedException extends RuntimeException {
    public ImageAlreadyAssociatedException(String message) {
        super(message);
    }
}

