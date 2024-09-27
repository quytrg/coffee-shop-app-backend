package com.project.coffeeshopapp.services.image;

import com.project.coffeeshopapp.customexceptions.ImageAlreadyAssociatedException;
import com.project.coffeeshopapp.dtos.response.imageupload.ImageUploadResponse;
import com.project.coffeeshopapp.dtos.result.imageupload.ImageUploadResult;
import com.project.coffeeshopapp.enums.ImageAssociationType;
import com.project.coffeeshopapp.mappers.ImageMapper;
import com.project.coffeeshopapp.models.Category;
import com.project.coffeeshopapp.models.Image;
import com.project.coffeeshopapp.models.Product;
import com.project.coffeeshopapp.models.User;
import com.project.coffeeshopapp.repositories.ImageRepository;
import com.project.coffeeshopapp.services.cloudinary.ICloudinaryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ImageService implements IImageService {
    private final ImageRepository imageRepository;
    private final ICloudinaryService cloudinaryService;
    private final ImageMapper imageMapper;

    @Override
    @Transactional
    public ImageUploadResponse uploadImage(MultipartFile file) throws IOException {
        ImageUploadResult imageUploadResult = cloudinaryService.uploadImage(file);
        Image newImage = imageMapper.imageUploadResultToImage(imageUploadResult);
        Image createdImage = imageRepository.save(newImage);
        return imageMapper.imageToImageUploadResponse(createdImage);
    }

    @Override
    public void deleteImage(String publicId) {

    }

    @Override
    public List<Image> associateImagesWithProduct(Product product, List<Long> imageIds) {
        return associateImagesWithEntity(product, imageIds, ImageAssociationType.PRODUCT);
    }

    @Override
    public List<Image> associateImagesWithCategory(Category category, List<Long> imageIds) {
        return associateImagesWithEntity(category, imageIds, ImageAssociationType.CATEGORY);
    }

    @Override
    public List<Image> associateImagesWithUser(User user, List<Long> imageIds) {
        return associateImagesWithEntity(user, imageIds, ImageAssociationType.USER);
    }

    private List<Image> associateImagesWithEntity(Object entity, List<Long> imageIds, ImageAssociationType associationType) {
        if (imageIds == null || imageIds.isEmpty()) {
            return Collections.emptyList();
        }

        List<Image> images = imageRepository.findAllById(imageIds);

        for (Image image : images) {
            if (image.getProduct() != null || image.getCategory() != null || image.getUser() != null) {
                throw new ImageAlreadyAssociatedException("Image with ID " + image.getId() + " is already associated with an entity.");
            }

            image.setImageAssociationType(associationType);

            switch (associationType) {
                case PRODUCT:
                    image.setProduct((Product) entity);
                    break;
                case CATEGORY:
                    image.setCategory((Category) entity);
                    break;
                case USER:
                    image.setUser((User) entity);
                    break;
                default:
                    throw new IllegalArgumentException("Unsupported entity type");
            }
        }

        return images;
    }
}
