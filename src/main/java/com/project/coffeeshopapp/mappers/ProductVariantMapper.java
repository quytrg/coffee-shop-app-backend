package com.project.coffeeshopapp.mappers;

import com.project.coffeeshopapp.dtos.request.productvariant.ProductVariantCreateRequest;
import com.project.coffeeshopapp.dtos.request.productvariant.ProductVariantUpdateRequest;
import com.project.coffeeshopapp.dtos.response.productvariant.ProductVariantResponse;
import com.project.coffeeshopapp.dtos.response.productvariant.ProductVariantSummaryResponse;
import com.project.coffeeshopapp.models.ProductVariant;
import org.mapstruct.*;

@Mapper(componentModel = "spring", uses = { ProductMapper.class, ProductVariantIngredientMapper.class})
public interface ProductVariantMapper {
    @Mapping(target = "product", ignore = true)
    @Mapping(target = "ingredients", ignore = true)
    ProductVariant productVariantCreateRequestToProductVariant(ProductVariantCreateRequest productVariantCreateRequest);

    @Mapping(source = "ingredients", target = "ingredients")
    ProductVariantResponse productVariantToProductVariantResponse(ProductVariant productVariant);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "product", ignore = true)
    @Mapping(target = "ingredients", ignore = true)
    void productVariantUpdateRequestToProductVariant(ProductVariantUpdateRequest productVariantUpdateRequest, @MappingTarget ProductVariant productVariant);

    ProductVariantSummaryResponse productVariantToProductVariantSummaryResponse(ProductVariant productVariant);
}
