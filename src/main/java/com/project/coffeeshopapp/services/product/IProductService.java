package com.project.coffeeshopapp.services.product;

import com.project.coffeeshopapp.dtos.request.product.ProductCreateRequest;
import com.project.coffeeshopapp.dtos.response.product.ProductResponse;

public interface IProductService {
    ProductResponse createProduct(ProductCreateRequest productCreateRequest);
}
