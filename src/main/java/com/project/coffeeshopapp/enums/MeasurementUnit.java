package com.project.coffeeshopapp.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum MeasurementUnit implements BaseEnum {
    // unit of mass
    GRAM("g", "Gram"),
    KILOGRAM("kg", "Kilogram"),

    // unit of volume
    MILLILITER("ml", "Milliliter"),
    LITER("l", "Liter"),

    // counting unit
    PIECE("pc", "Piece"), // use for cake, cookie,...
    SCOOP("scoop", "Scoop"), // use for cream, powder,...

    // bartending unit
    SHOT("shot", "Shot"), // use for espresso
    PUMP("pump", "Pump"), // use for syrup
    CUP("cup", "Cup"), // unit of recipe

    // spoon unit
    TABLESPOON("tbsp", "Tablespoon"),
    TEASPOON("tsp", "Teaspoon"),

    // special unit for topping
    PORTION("portion", "Portion"), // use for topping, whipped cream
    DROP("drop", "Drop"); // use for essence, extract

    private final String symbol;
    private final String value;
}
