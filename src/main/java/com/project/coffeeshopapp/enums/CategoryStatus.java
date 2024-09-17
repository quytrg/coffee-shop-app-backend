package com.project.coffeeshopapp.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.project.coffeeshopapp.customexceptions.InvalidEnumValueException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

@Getter
@RequiredArgsConstructor
public enum CategoryStatus {
    ACTIVE("Active"),
    INACTIVE("Inactive");

    private final String statusName;

//    // Cache enum values for performance (prevents repeated calls to values())
//    private static final CategoryStatus[] CACHED_VALUES = values();
//
//    @JsonCreator(mode = JsonCreator.Mode.DELEGATING)
//    public static CategoryStatus parseValue(String s) {
//        return s == null ? null :
//                Arrays.stream(CACHED_VALUES)
//                        .filter(value -> value.name().equalsIgnoreCase(s))
//                        .findFirst()
//                        .orElseThrow(() -> new InvalidEnumValueException("status", "Invalid enum value: " + s));
//    }
}
