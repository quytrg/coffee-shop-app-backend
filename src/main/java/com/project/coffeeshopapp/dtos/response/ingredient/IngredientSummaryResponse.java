package com.project.coffeeshopapp.dtos.response.ingredient;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class IngredientSummaryResponse {
    private Long id;
    private String name;
    private String description;
}