package com.project.coffeeshopapp.dtos.request.ingredient;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class IngredientCreateRequest {
    @NotBlank(message = "Ingredient name cannot be blank")
    @Size(min = 2, max = 500, message = "Name length must be between {min} and {max} characters")
    private String name;

    @Size(max = 2000, message = "Description cannot exceed 2000 characters")
    private String description;

    @Size(max = 2000, message = "Storage instructions cannot exceed 2000 characters")
    private String storageInstructions;
}
