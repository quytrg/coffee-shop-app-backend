package com.project.coffeeshopapp.controllers;

import com.project.coffeeshopapp.dtos.request.product.ProductCreateRequest;
import com.project.coffeeshopapp.dtos.request.product.ProductSearchRequest;
import com.project.coffeeshopapp.dtos.request.product.ProductUpdateRequest;
import com.project.coffeeshopapp.dtos.request.productvariant.ProductVariantCreateRequest;
import com.project.coffeeshopapp.dtos.request.productvariant.ProductVariantSearchRequest;
import com.project.coffeeshopapp.dtos.request.productvariant.ProductVariantUpdateRequest;
import com.project.coffeeshopapp.dtos.response.api.SuccessResponse;
import com.project.coffeeshopapp.dtos.response.pagination.PaginationResponse;
import com.project.coffeeshopapp.dtos.response.product.ProductResponse;
import com.project.coffeeshopapp.dtos.response.product.ProductSummaryResponse;
import com.project.coffeeshopapp.dtos.response.productvariant.ProductVariantResponse;
import com.project.coffeeshopapp.dtos.response.productvariant.ProductVariantSummaryResponse;
import com.project.coffeeshopapp.services.product.ProductService;
import com.project.coffeeshopapp.services.productvariant.ProductVariantService;
import com.project.coffeeshopapp.utils.PaginationUtil;
import com.project.coffeeshopapp.utils.ResponseUtil;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Validated
@RequestMapping("${api.prefix}/products")
public class ProductController {
    private final ProductService productService;
    private final ResponseUtil responseUtil;
    private final PaginationUtil paginationUtil;
    private final ProductVariantService productVariantService;

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

    @PatchMapping("/{id}")
    public ResponseEntity<SuccessResponse<ProductResponse>> updateProduct(
            @PathVariable(name = "id") Long id,
            @Valid @RequestBody ProductUpdateRequest productUpdateRequest) {
        ProductResponse productResponse = productService.updateProduct(id, productUpdateRequest);
        return responseUtil.createSuccessResponse(
                productResponse,
                "Product was updated successfully",
                HttpStatus.OK
        );
    }

    @GetMapping
    public ResponseEntity<SuccessResponse<PaginationResponse<ProductSummaryResponse>>> getAllProducts(
            @Valid @ModelAttribute ProductSearchRequest request) {
        Page<ProductSummaryResponse> productSummaryResponsePage = productService.getAllProducts(request);
        PaginationResponse<ProductSummaryResponse> paginationResponse = paginationUtil.createPaginationResponse(
                productSummaryResponsePage
        );
        return responseUtil.createSuccessResponse(
                paginationResponse,
                "Retrieve paginated products successfully",
                HttpStatus.OK
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<SuccessResponse<ProductResponse>> getProductById(
            @PathVariable(name = "id") Long id) {
        ProductResponse productResponse = productService.getProductById(id);
        return responseUtil.createSuccessResponse(
                productResponse,
                "Get product successfully",
                HttpStatus.OK
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<SuccessResponse<?>> deleteProduct(
            @PathVariable(name = "id") Long id) {
        productService.softDeleteProduct(id);
        return responseUtil.createSuccessResponseWithoutData(
                "Product successfully deleted",
                HttpStatus.OK
        );
    }

    @PostMapping("/{productId}/variants")
    public ResponseEntity<SuccessResponse<ProductVariantResponse>> createProductVariant(
            @PathVariable Long productId,
            @Valid @RequestBody ProductVariantCreateRequest productVariantCreateRequest) {
        ProductVariantResponse productVariantResponse = productVariantService
                .createProductVariant(productId, productVariantCreateRequest);
        return responseUtil.createSuccessResponse(
                productVariantResponse,
                "Product variant created successfully",
                HttpStatus.CREATED
        );
    }

    @PatchMapping("/{productId}/variants/{variantId}")
    public ResponseEntity<SuccessResponse<ProductVariantResponse>> updateProductVariant(
            @PathVariable Long productId,
            @PathVariable Long variantId,
            @Valid @RequestBody ProductVariantUpdateRequest productVariantUpdateRequest) {
        ProductVariantResponse productVariantResponse = productVariantService.updateProductVariant(productId, variantId, productVariantUpdateRequest);
        return responseUtil.createSuccessResponse(
                productVariantResponse,
                "Product variant updated successfully",
                HttpStatus.OK
        );
    }

    @GetMapping("/{productId}/variants")
    public ResponseEntity<SuccessResponse<PaginationResponse<ProductVariantSummaryResponse>>> getProductVariants(
            @PathVariable Long productId,
            @Valid @ModelAttribute ProductVariantSearchRequest request) {
        Page<ProductVariantSummaryResponse> productVariantSummaryResponsePage = productVariantService
                .getProductVariants(productId, request);
        PaginationResponse<ProductVariantSummaryResponse> paginationResponse = paginationUtil
                .createPaginationResponse(productVariantSummaryResponsePage);
        return responseUtil.createSuccessResponse(
                paginationResponse,
                "Product variants fetched successfully",
                HttpStatus.OK
        );
    }
}
