package com.project.coffeeshopapp.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum OrderStatus implements BaseEnum {
    PENDING("pending"),
    PROCESSING("processing"),
    COMPLETED("completed"),
    CANCELED("canceled");
    private final String value;
}
