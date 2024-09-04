package com.project.coffeeshopapp.customexceptions;

import lombok.Getter;

@Getter
public class DataNotFoundException extends RuntimeException {
    private final String entityName;

    public DataNotFoundException(String entityName, String message) {
        super(message);
        this.entityName = entityName;
    }
}
