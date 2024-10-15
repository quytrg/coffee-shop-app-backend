package com.project.coffeeshopapp.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum PaymentMethod implements BaseEnum {
    CASH("cash"),
    BANK_TRANSFER("bankTransfer"),
    CREDIT("credit"),
    E_WALLET("eWallet");
    private final String value;
}
