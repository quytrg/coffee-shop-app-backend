package com.project.coffeeshopapp.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum StockBatchSortField implements BaseEnum {
    ID("id"),
    INGREDIENT_NAME("ingredientName"),
    REMAINING_QUANTITY("remainingQuantity"),
    RECEIVED_DATE("receivedDate"),
    EXPIRATION_DATE("expirationDate"),
    SUPPLIER_NAME("supplierName"),
    CREATED_AT("createdAt"),
    UPDATED_AT("updatedAt");
    private final String value;
}
