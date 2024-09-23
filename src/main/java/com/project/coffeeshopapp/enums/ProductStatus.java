package com.project.coffeeshopapp.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ProductStatus {
    ACTIVE("Active"),
    INACTIVE("Inactive"),
    OUT_OF_STOCK("OutOfStock");
    private final String value;
}