package com.project.coffeeshopapp.dtos.request.stockbatch;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StockBatchDeductRequest {
    @NotNull(message = "Ingredient ID cannot be null")
    private Long ingredientId;

    @NotNull(message = "Quantity cannot be null")
    @Positive(message = "Quantity must be positive")
    private BigDecimal quantity;
}
