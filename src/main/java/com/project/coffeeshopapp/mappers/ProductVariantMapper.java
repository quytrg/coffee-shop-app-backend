package com.project.coffeeshopapp.mappers;

import com.project.coffeeshopapp.dtos.request.productvariant.ProductVariantCreateRequest;
import com.project.coffeeshopapp.dtos.response.productvariant.ProductVariantResponse;
import com.project.coffeeshopapp.models.ProductVariant;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = { ProductMapper.class })
public interface ProductVariantMapper {
    @Mapping(target = "product", ignore = true)
    ProductVariant productVariantCreateRequestToProductVariant(ProductVariantCreateRequest productVariantCreateRequest);

    ProductVariantResponse productVariantToProductVariantResponse(ProductVariant productVariant);
}
