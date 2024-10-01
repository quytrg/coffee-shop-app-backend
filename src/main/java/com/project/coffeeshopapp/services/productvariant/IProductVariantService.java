package com.project.coffeeshopapp.services.productvariant;

import com.project.coffeeshopapp.dtos.request.productvariant.ProductVariantCreateRequest;
import com.project.coffeeshopapp.dtos.response.productvariant.ProductVariantResponse;

public interface IProductVariantService {
    ProductVariantResponse createProductVariant(Long productId, ProductVariantCreateRequest productVariantCreateRequest);
}

