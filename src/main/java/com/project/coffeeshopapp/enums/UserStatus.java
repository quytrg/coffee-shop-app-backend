package com.project.coffeeshopapp.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum UserStatus implements BaseEnum{
    ACTIVE("active"),
    INACTIVE("inactive");
    private final String value;
}