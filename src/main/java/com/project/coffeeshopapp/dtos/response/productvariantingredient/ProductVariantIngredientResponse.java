package com.project.coffeeshopapp.dtos.response.productvariantingredient;

import com.project.coffeeshopapp.enums.MeasurementUnit;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductVariantIngredientResponse {
    private Long ingredientId;
    private String ingredientName;
    private BigDecimal quantity;
    private MeasurementUnit unit;
    private Integer preparationOrder;
    private String description;
}
