package com.project.coffeeshopapp.dtos.response.productvariant;

import com.project.coffeeshopapp.dtos.response.product.ProductSummaryResponse;
import com.project.coffeeshopapp.dtos.response.productvariantingredient.ProductVariantIngredientResponse;
import com.project.coffeeshopapp.enums.ProductSize;
import com.project.coffeeshopapp.enums.ProductVariantStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductVariantResponse {
    private Long id;
    private ProductSummaryResponse product;
    private ProductSize size;
    private ProductVariantStatus status;
    private BigDecimal price;
    private BigDecimal cost;
    private String description;
    private List<ProductVariantIngredientResponse> ingredients;
}
