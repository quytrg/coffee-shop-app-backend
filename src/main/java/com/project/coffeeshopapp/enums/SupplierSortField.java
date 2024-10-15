package com.project.coffeeshopapp.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum SupplierSortField implements BaseEnum{
    NAME("name"),
    RATING("rating"),
    CREATED_AT("createdAt"),
    UPDATED_AT("updatedAt");
    private final String value;
}
