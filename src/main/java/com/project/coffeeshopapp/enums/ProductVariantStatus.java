package com.project.coffeeshopapp.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ProductVariantStatus implements BaseEnum{
    ACTIVE("active"),
    INACTIVE("inactive"),
    OUT_OF_STOCK("outOfStock");
    private final String value;
}
