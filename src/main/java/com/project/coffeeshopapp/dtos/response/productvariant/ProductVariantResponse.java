package com.project.coffeeshopapp.dtos.response.productvariant;

import com.project.coffeeshopapp.dtos.response.product.ProductSummaryResponse;
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

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductVariantResponse {
    private ProductSummaryResponse product;
    private ProductSize size;
    private ProductVariantStatus status;
    private BigDecimal price;
    private BigDecimal cost;
    private String description;
}
