package com.project.coffeeshopapp.dtos.response.product;

import com.project.coffeeshopapp.enums.ProductStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductSummaryResponse {
    private Long id;
    private String name;
    private Long position;
    private List<String> imageUrls;
    private ProductStatus status;
    private Long categoryId;
    private String categoryName;
}