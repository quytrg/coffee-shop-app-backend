package com.project.coffeeshopapp.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ProductSize implements BaseEnum{
    S("S"),
    M("M"),
    L("L");
    private final String value;
}
