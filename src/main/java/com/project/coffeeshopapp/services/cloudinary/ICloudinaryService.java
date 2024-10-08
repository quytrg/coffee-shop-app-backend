package com.project.coffeeshopapp.services.cloudinary;

import com.project.coffeeshopapp.dtos.result.imageupload.ImageUploadResult;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface ICloudinaryService {
    ImageUploadResult uploadImage(MultipartFile file) throws IOException;
    void deleteImage(String publicId) throws IOException;
}
