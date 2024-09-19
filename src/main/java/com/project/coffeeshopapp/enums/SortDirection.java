package com.project.coffeeshopapp.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum SortDirection implements SortField {
    ASC("asc"),
    DESC("desc");
    private final String value;
}
