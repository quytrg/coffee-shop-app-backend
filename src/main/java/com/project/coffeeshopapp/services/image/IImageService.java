package com.project.coffeeshopapp.services.image;

import com.project.coffeeshopapp.dtos.response.imageupload.ImageUploadResponse;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface IImageService {
    ImageUploadResponse uploadImage(MultipartFile file) throws IOException;
    void deleteImage(String publicId) throws IOException;
}
