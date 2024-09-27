package com.project.coffeeshopapp.dtos.response.product;

import com.project.coffeeshopapp.enums.ProductStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductSummaryResponse {
    private Long id;
    private String name;
    private String imageUrl;
    private ProductStatus status;
    private Long categoryId;
    private String categoryName;
}