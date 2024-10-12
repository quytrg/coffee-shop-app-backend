package com.project.coffeeshopapp.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum PaymentStatus implements BaseEnum{
    UNPAID("unpaid"),
    PARTIALLY_PAID("partiallyPaid"),
    PAID("paid"),
    OVERDUE("overdue"),
    REFUNDED("refunded");
    private final String value;
}
