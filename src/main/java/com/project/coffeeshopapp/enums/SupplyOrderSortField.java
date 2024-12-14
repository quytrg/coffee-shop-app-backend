package com.project.coffeeshopapp.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum SupplyOrderSortField implements BaseEnum {
    ORDER_CODE("orderCode"),
    CREATED_AT("createdAt"),
    UPDATED_AT("updatedAt"),
    TOTAL_AMOUNT("totalAmount"),
    EXPECTED_DELIVERY_DATE("expectedDeliveryDate"),
    ACTUAL_DELIVERY_DATE("actualDeliveryDate");
    private final String value;
}
