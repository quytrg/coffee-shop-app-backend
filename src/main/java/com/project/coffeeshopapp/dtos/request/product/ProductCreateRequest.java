package com.project.coffeeshopapp.dtos.request.product;

import com.project.coffeeshopapp.enums.ProductStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

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

    private Long position;

    @NotNull(message = "Product ID is required")
    private Long categoryId;

    @NotEmpty(message = "Image ids cannot be empty")
    @Size(min = 1, message = "Image ids must contain at least one value")
    private List<Long> imageIds = new ArrayList<>();
}
