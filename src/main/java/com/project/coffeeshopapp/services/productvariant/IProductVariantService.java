package com.project.coffeeshopapp.services.productvariant;

import com.project.coffeeshopapp.dtos.request.productvariant.ProductVariantCreateRequest;
import com.project.coffeeshopapp.dtos.request.productvariant.ProductVariantSearchRequest;
import com.project.coffeeshopapp.dtos.request.productvariant.ProductVariantUpdateRequest;
import com.project.coffeeshopapp.dtos.response.productvariant.ProductVariantResponse;
import com.project.coffeeshopapp.dtos.response.productvariant.ProductVariantSummaryResponse;
import org.springframework.data.domain.Page;

public interface IProductVariantService {
    ProductVariantResponse createProductVariant(Long productId, ProductVariantCreateRequest productVariantCreateRequest);
    ProductVariantResponse updateProductVariant(Long productId, Long variantId, ProductVariantUpdateRequest productVariantUpdateRequest);
    Page<ProductVariantSummaryResponse> getProductVariants(Long productId, ProductVariantSearchRequest productVariantSearchRequest);
    ProductVariantResponse getProductVariant(Long productId, Long variantId);
    void softDeleteProductVariant(Long productId, Long variantId);
}

