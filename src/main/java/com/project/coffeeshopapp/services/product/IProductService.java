package com.project.coffeeshopapp.services.product;

import com.project.coffeeshopapp.dtos.request.product.ProductCreateRequest;
import com.project.coffeeshopapp.dtos.request.product.ProductSearchRequest;
import com.project.coffeeshopapp.dtos.request.product.ProductUpdateRequest;
import com.project.coffeeshopapp.dtos.response.product.ProductResponse;
import com.project.coffeeshopapp.dtos.response.product.ProductSummaryResponse;
import org.springframework.data.domain.Page;

public interface IProductService {
    ProductResponse createProduct(ProductCreateRequest productCreateRequest);
    ProductResponse updateProduct(Long id, ProductUpdateRequest productUpdateRequest);
    Page<ProductSummaryResponse> getAllProducts(ProductSearchRequest request);
    ProductResponse getProductById(Long id);
}
