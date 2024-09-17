package com.project.coffeeshopapp.dtos.request.category;

import com.project.coffeeshopapp.enums.CategoryStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoryUpdateRequest {
    @Size(min = 1, max = 200, message = "Name length must be between {min} and {max} characters")
    private String name;

    @Size(max = 2000, message = "Description cannot exceed 2000 characters")
    private String description;

    private CategoryStatus status;
}
