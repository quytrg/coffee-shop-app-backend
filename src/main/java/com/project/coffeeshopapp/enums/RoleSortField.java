package com.project.coffeeshopapp.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum RoleSortField implements BaseEnum {
    NAME("name"),
    CREATED_AT("createdAt"),
    UPDATED_AT("updatedAt");
    private final String value;
}