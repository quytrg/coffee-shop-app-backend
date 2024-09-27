package com.project.coffeeshopapp.mappers;

import com.project.coffeeshopapp.dtos.response.imageupload.ImageUploadResponse;
import com.project.coffeeshopapp.dtos.result.imageupload.ImageUploadResult;
import com.project.coffeeshopapp.models.Image;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ImageMapper {
    Image imageUploadResultToImage(ImageUploadResult imageUploadResult);
    @Mapping(source = "id", target = "imageId")
    ImageUploadResponse imageToImageUploadResponse(Image image);
}
