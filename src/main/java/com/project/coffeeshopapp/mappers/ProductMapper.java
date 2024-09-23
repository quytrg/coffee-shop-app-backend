package com.project.coffeeshopapp.mappers;

import com.project.coffeeshopapp.dtos.request.product.ProductCreateRequest;
import com.project.coffeeshopapp.dtos.response.category.CategorySummaryResponse;
import com.project.coffeeshopapp.dtos.response.product.ProductResponse;
import com.project.coffeeshopapp.models.Category;
import com.project.coffeeshopapp.models.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    @Mapping(target = "category", ignore = true)
    Product productCreateRequestToProduct(ProductCreateRequest productCreateRequest);

    ProductResponse productToProductResponse(Product product);
    //  mapping beans as child beans Category -> CategorySummaryResponse
    CategorySummaryResponse categoryToCategorySummaryResponse(Category category);
}
