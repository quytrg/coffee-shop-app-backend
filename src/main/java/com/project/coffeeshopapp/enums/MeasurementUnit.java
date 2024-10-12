package com.project.coffeeshopapp.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum MeasurementUnit implements BaseEnum {
    // unit of mass
    GRAM("g", "gram"),
    KILOGRAM("kg", "kilogram"),

    // unit of volume
    MILLILITER("ml", "milliliter"),
    LITER("l", "liter"),

    // counting unit
    PIECE("pc", "piece"), // use for cake, cookie,...
    SCOOP("scoop", "scoop"), // use for cream, powder,...

    // bartending unit
    SHOT("shot", "shot"), // use for espresso
    PUMP("pump", "pump"), // use for syrup
    CUP("cup", "cup"), // unit of recipe

    // spoon unit
    TABLESPOON("tbsp", "tablespoon"),
    TEASPOON("tsp", "teaspoon"),

    // special unit for topping
    PORTION("portion", "portion"), // use for topping, whipped cream
    DROP("drop", "drop"); // use for essence, extract

    private final String symbol;
    private final String value;
}
