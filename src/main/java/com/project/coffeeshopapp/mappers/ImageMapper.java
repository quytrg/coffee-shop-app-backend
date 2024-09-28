package com.project.coffeeshopapp.mappers;

import com.project.coffeeshopapp.dtos.response.image.ImageSummaryResponse;
import com.project.coffeeshopapp.dtos.response.imageupload.ImageUploadResponse;
import com.project.coffeeshopapp.dtos.result.imageupload.ImageUploadResult;
import com.project.coffeeshopapp.models.Image;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ImageMapper {
    Image imageUploadResultToImage(ImageUploadResult imageUploadResult);
    @Mapping(source = "id", target = "imageId")
    ImageUploadResponse imageToImageUploadResponse(Image image);

    // Map Image entity to ImageSummaryResponse DTO
    ImageSummaryResponse imageToImageSummaryResponse(Image image);
    // Map List<Image> to List<ImageSummaryResponse>
    List<ImageSummaryResponse> imagesToImageSummaryResponses(List<Image> images);

    // Map Image to URL
    default String imageToUrl(Image image) {
        return image.getUrl();
    }
    // Map List<Image> to List<String> imageUrls
    List<String> imagesToImageUrls(List<Image> images);
}
