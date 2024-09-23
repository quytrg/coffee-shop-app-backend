package com.project.coffeeshopapp.dtos.request.product;

import com.project.coffeeshopapp.enums.ProductStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductCreateRequest {
    @NotBlank(message = "Product name cannot be blank")
    @Size(min = 2, max = 200, message = "Name length must be between {min} and {max} characters")
    private String name;

    @Size(max = 2000, message = "Description cannot exceed 2000 characters")
    private String description;

    @NotNull(message = "Product status is required")
    private ProductStatus status;

    @NotNull(message = "Category id is required")
    private Long categoryId;
}
