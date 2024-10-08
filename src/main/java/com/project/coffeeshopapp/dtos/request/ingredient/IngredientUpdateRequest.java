package com.project.coffeeshopapp.dtos.request.ingredient;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class IngredientUpdateRequest {
    private String name;
    private String description;
    private String storageInstructions;
}
