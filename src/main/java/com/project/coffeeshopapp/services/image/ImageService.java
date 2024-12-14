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
}
