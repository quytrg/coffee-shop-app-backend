package com.project.coffeeshopapp.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum MeasurementUnit implements BaseEnum {
    // unit of mass
    GRAM("g", "gram"),
    KILOGRAM("kg", "kilogram"),

    // unit of volume
    MILLILITER("ml", "milliliter"),
    LITER("l", "liter");

    private final String symbol;
    private final String value;
}
