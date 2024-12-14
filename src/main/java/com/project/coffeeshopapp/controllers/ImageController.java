package com.project.coffeeshopapp.controllers;

import com.project.coffeeshopapp.dtos.response.api.ErrorResponse;
import com.project.coffeeshopapp.dtos.response.api.SuccessResponse;
import com.project.coffeeshopapp.dtos.response.imageupload.ImageUploadResponse;
import com.project.coffeeshopapp.services.image.IImageService;
import com.project.coffeeshopapp.utils.ResponseUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Collections;
import java.util.List;


@RestController
@RequiredArgsConstructor
@Validated
@RequestMapping("/${api.prefix}/images")
public class ImageController {
    private final IImageService imageService;
    private final ResponseUtil responseUtil;

    @PostMapping("/upload")
    public ResponseEntity<?> uploadImage(@RequestParam("file") MultipartFile file) {
        try {
            ImageUploadResponse imageUploadResponse = imageService.uploadImage(file);
            return responseUtil.createSuccessResponse(
                    imageUploadResponse,
                    "Image uploaded successfully",
                    HttpStatus.OK
            );
        } catch (IOException ex) {
            List<ErrorResponse.ErrorDetail> errorDetails = Collections.singletonList(
                    ErrorResponse.ErrorDetail.builder()
                            .field("system")
                            .message(ex.getMessage())
                            .build()
            );
            // Handle exception
            return responseUtil.createErrorResponse(
                    "Error uploading image",
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    errorDetails
            );
        }
    }
}

