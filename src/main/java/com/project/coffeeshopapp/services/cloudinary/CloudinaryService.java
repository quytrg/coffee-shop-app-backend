package com.project.coffeeshopapp.services.cloudinary;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.project.coffeeshopapp.dtos.result.imageupload.ImageUploadResult;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class CloudinaryService implements ICloudinaryService {
    private final Cloudinary cloudinary;

    @Override
    public ImageUploadResult uploadImage(MultipartFile file) throws IOException {
        String fileName = file.getOriginalFilename();
        System.out.println("Original file name: " + fileName);

        Map<String, Object> params = ObjectUtils.asMap(
                "folder", "coffee-shop-app",
                "use_filename", true,
                "unique_filename", true
        );
        Map<?, ?> uploadResult = cloudinary.uploader().upload(file.getBytes(), params);
        ImageUploadResult result = new ImageUploadResult();
        result.setUrl(uploadResult.get("secure_url").toString());
        result.setPublicId(uploadResult.get("public_id").toString());
        return result;
    }

    @Override
    public void deleteImage(String publicId) throws IOException {
        Map<?, ?> deleteResult = cloudinary.uploader().destroy(publicId, ObjectUtils.emptyMap());
        // handle...
    }
}

