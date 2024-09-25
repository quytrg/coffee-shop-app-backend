package com.project.coffeeshopapp.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum UserSortField implements BaseEnum {
    FULL_NAME("fullName"),
    CREATED_AT("createdAt"),
    UPDATED_AT("updatedAt");
    private final String value;
}
