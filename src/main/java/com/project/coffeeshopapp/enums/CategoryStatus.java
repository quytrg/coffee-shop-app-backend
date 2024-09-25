package com.project.coffeeshopapp.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.project.coffeeshopapp.customexceptions.InvalidEnumValueException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

@Getter
@RequiredArgsConstructor
public enum CategoryStatus implements BaseEnum {
    ACTIVE("active"),
    INACTIVE("inactive");

    private final String value;
}
