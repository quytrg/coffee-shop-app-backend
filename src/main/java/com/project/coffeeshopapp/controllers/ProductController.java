package com.project.coffeeshopapp.controllers;

import com.project.coffeeshopapp.dtos.request.product.ProductCreateRequest;
import com.project.coffeeshopapp.dtos.request.user.UserCreateRequest;
import com.project.coffeeshopapp.dtos.response.api.SuccessResponse;
import com.project.coffeeshopapp.dtos.response.product.ProductResponse;
import com.project.coffeeshopapp.dtos.response.user.UserResponse;
import com.project.coffeeshopapp.services.product.ProductService;
import com.project.coffeeshopapp.utils.ResponseUtil;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Validated
@RequestMapping("${api.prefix}/products")
public class ProductController {
    private final ProductService productService;
    private final ResponseUtil responseUtil;

    @PostMapping()
    public ResponseEntity<SuccessResponse<ProductResponse>> createProduct(
            @Valid @RequestBody ProductCreateRequest productCreateRequest) {
        ProductResponse productResponse = productService.createProduct(productCreateRequest);
        return responseUtil.createSuccessResponse(
                productResponse,
                "Product was created successfully",
                HttpStatus.CREATED
        );
    }
}
