package com.project.coffeeshopapp.controllers;

import com.project.coffeeshopapp.dtos.request.product.ProductCreateRequest;
import com.project.coffeeshopapp.dtos.request.product.ProductUpdateRequest;
import com.project.coffeeshopapp.dtos.request.product.ProductSearchRequest;
import com.project.coffeeshopapp.dtos.response.api.SuccessResponse;
import com.project.coffeeshopapp.dtos.response.pagination.PaginationResponse;
import com.project.coffeeshopapp.dtos.response.product.ProductResponse;
import com.project.coffeeshopapp.dtos.response.product.ProductSummaryResponse;
import com.project.coffeeshopapp.mappers.ProductMapper;
import com.project.coffeeshopapp.services.product.ProductService;
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
}
