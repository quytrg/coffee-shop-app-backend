package com.project.coffeeshopapp.dtos.response.productvariant;

import com.project.coffeeshopapp.enums.ProductSize;
import com.project.coffeeshopapp.enums.ProductVariantStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductVariantSummaryResponse {
    private Long id;
    private ProductSize size;
    private ProductVariantStatus status;
    private BigDecimal price;
}
