package com.project.coffeeshopapp.dtos.request.productvariantingredient;

import com.project.coffeeshopapp.enums.MeasurementUnit;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductVariantIngredientRequest {
    @NotNull(message = "IngredientId is required")
    private Long ingredientId;
    @NotNull(message = "Ingredient quantity is mandatory")
    @PositiveOrZero(message = "Quantity must be non-negative value")
    private BigDecimal quantity;
    @NotNull(message = "Measurement unit is required")
    private MeasurementUnit unit;
    @NotNull(message = "Preparation order is required")
    private Integer preparationOrder;
    private String description;
}
