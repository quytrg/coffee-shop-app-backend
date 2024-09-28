package com.project.coffeeshopapp.dtos.response.image;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ImageSummaryResponse {
    private Long id;
    private String url;
    private String altText;
    private Integer displayOrder;
}
