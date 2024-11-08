package com.project.coffeeshopapp.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum OrderItemReportSortField implements BaseEnum{
    ID("id"),
    QUANTITY("quantity"),
    PRICE("price"),
    SUBTOTAL("subtotal"),
    PRODUCT_NAME("productName"),
    PRODUCT_VARIANT_SIZE("productVariantSize"),
    DISCOUNT("discount"),
    ORDER_CODE("orderCode"),
    ORDER_STATUS("orderStatus"),
    CREATED_AT("createdAt"),
    UPDATED_AT("updatedAt"),
    CREATED_BY("createdBy"),
    UPDATED_BY("updatedBy");
    private final String value;
}
