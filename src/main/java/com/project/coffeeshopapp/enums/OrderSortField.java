package com.project.coffeeshopapp.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum OrderSortField implements BaseEnum {
    CUSTOMER_NAME("customerName"),
    STATUS("status"),
    ORDER_CODE("orderCode"),
    CREATED_AT("createdAt"),
    UPDATED_AT("updatedAt"),
    TOTAL_AMOUNT("totalAmount"),
    RECEIVED_AMOUNT("receivedAmount"),
    RETURN_AMOUNT("returnAmount");
    private final String value;
}