package com.project.coffeeshopapp.dtos.response.imageupload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ImageUploadResponse {
    private Long imageId;
    private String url;
}