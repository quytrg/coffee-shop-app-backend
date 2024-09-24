package com.project.coffeeshopapp.dtos.request.product;

import com.project.coffeeshopapp.enums.ProductStatus;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductUpdateRequest {
    @Size(min = 2, max = 200, message = "Name length must be between {min} and {max} characters")
    private String name;

    @Size(max = 2000, message = "Description cannot exceed 2000 characters")
    private String description;

    private ProductStatus status;

    private Long categoryId;
}