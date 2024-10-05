package com.project.coffeeshopapp.dtos.request.productvariant;

import com.project.coffeeshopapp.dtos.request.productvariantingredient.ProductVariantIngredientRequest;
import com.project.coffeeshopapp.enums.ProductSize;
import com.project.coffeeshopapp.enums.ProductVariantStatus;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductVariantCreateRequest {
    @NotNull(message = "Product size is required")
    private ProductSize size;

    @NotNull(message = "Product variant status is required")
    private ProductVariantStatus status;

    @NotNull(message = "Price is mandatory")
    @PositiveOrZero(message = "Price must be non-negative value")
    @DecimalMax(value = "999999.99", message = "Price cannot exceed 999,999.99")
    private BigDecimal price;

    @NotNull(message = "Cost is mandatory")
    @PositiveOrZero(message = "Cost must be non-negative value")
    @DecimalMax(value = "999999.99", message = "Cost cannot exceed 999,999.99")
    private BigDecimal cost;

    @Size(max = 2000, message = "Description cannot exceed 2000 characters")
    private String description;

    private List<ProductVariantIngredientRequest> ingredients = new ArrayList<>();
}
