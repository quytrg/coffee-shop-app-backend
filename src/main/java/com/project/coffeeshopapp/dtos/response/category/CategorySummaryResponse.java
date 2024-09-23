package com.project.coffeeshopapp.dtos.response.category;

import com.project.coffeeshopapp.enums.CategoryStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategorySummaryResponse {
    private Long id;
    private String name;
    private String description;
    private CategoryStatus status;
}
