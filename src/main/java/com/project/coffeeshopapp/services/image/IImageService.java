package com.project.coffeeshopapp.services.image;

import com.project.coffeeshopapp.dtos.response.imageupload.ImageUploadResponse;
import com.project.coffeeshopapp.enums.ImageAssociationType;
import com.project.coffeeshopapp.models.Category;
import com.project.coffeeshopapp.models.Image;
import com.project.coffeeshopapp.models.Product;
import com.project.coffeeshopapp.models.User;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface IImageService {
    ImageUploadResponse uploadImage(MultipartFile file) throws IOException;
    void deleteImage(String publicId) throws IOException;
    List<Image> associateImagesWithProduct(Product product, List<Long> imageIds);
    List<Image> associateImagesWithCategory(Category category, List<Long> imageIds);
    List<Image> associateImagesWithUser(User user, List<Long> imageIds);
}
