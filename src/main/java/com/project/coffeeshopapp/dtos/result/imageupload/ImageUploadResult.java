package com.project.coffeeshopapp.dtos.result.imageupload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ImageUploadResult {
    private String url;
    private String publicId;
}
