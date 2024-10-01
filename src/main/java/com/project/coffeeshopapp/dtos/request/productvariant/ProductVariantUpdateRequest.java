package com.project.coffeeshopapp.dtos.request.productvariant;

import com.project.coffeeshopapp.enums.ProductSize;
import com.project.coffeeshopapp.enums.ProductVariantStatus;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductVariantUpdateRequest {
    private ProductSize size;
    private ProductVariantStatus status;

    @PositiveOrZero(message = "Price must be non-negative value")
    @DecimalMax(value = "999999.99", message = "Price cannot exceed 999,999.99")
    private BigDecimal price;

    @PositiveOrZero(message = "Cost must be non-negative value")
    @DecimalMax(value = "999999.99", message = "Cost cannot exceed 999,999.99")
    private BigDecimal cost;

    @Size(max = 2000, message = "Description cannot exceed 2000 characters")
    private String description;
}
