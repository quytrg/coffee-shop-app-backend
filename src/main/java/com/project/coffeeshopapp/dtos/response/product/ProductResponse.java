package com.project.coffeeshopapp.dtos.response.product;

import com.project.coffeeshopapp.dtos.response.category.CategorySummaryResponse;
import com.project.coffeeshopapp.dtos.response.image.ImageSummaryResponse;
import com.project.coffeeshopapp.enums.ProductStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductResponse {
    private Long id;
    private String name;
    private String description;
    private Long position;
    private ProductStatus status;
    private CategorySummaryResponse category;
    private List<ImageSummaryResponse> images = new ArrayList<>();
}
