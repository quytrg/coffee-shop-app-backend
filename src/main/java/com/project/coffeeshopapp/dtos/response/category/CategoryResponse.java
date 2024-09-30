package com.project.coffeeshopapp.dtos.response.category;

import com.project.coffeeshopapp.dtos.response.image.ImageSummaryResponse;
import com.project.coffeeshopapp.enums.CategoryStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoryResponse {
    private Long id;
    private String name;
    private String description;
    private CategoryStatus status;
    private List<ImageSummaryResponse> images = new ArrayList<>();
}
