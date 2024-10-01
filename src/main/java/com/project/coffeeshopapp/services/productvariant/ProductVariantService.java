package com.project.coffeeshopapp.services.productvariant;

import com.project.coffeeshopapp.customexceptions.DataNotFoundException;
import com.project.coffeeshopapp.dtos.request.productvariant.ProductVariantCreateRequest;
import com.project.coffeeshopapp.dtos.response.productvariant.ProductVariantResponse;
import com.project.coffeeshopapp.mappers.ProductVariantMapper;
import com.project.coffeeshopapp.models.Product;
import com.project.coffeeshopapp.models.ProductVariant;
import com.project.coffeeshopapp.repositories.ProductRepository;
import com.project.coffeeshopapp.repositories.ProductVariantRepository;
import com.project.coffeeshopapp.utils.PaginationUtil;
import com.project.coffeeshopapp.utils.SortUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ProductVariantService implements IProductVariantService {
    private final ProductVariantMapper productVariantMapper;
    private final ProductVariantRepository productVariantRepository;
    private final PaginationUtil paginationUtil;
    private final SortUtil sortUtil;
    private final ProductRepository productRepository;

    @Override
    @Transactional
    public ProductVariantResponse createProductVariant(
            Long productId,
            ProductVariantCreateRequest productVariantCreateRequest) {
        // map ProductVariantCreateRequest to ProductVariant
        ProductVariant productVariant = productVariantMapper
                .productVariantCreateRequestToProductVariant(productVariantCreateRequest);
        // check if productId exists
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new DataNotFoundException("product",
                        "Product not found with id: " + productId));
        // set product for product variant
        productVariant.setProduct(product);
        // create product variant
        ProductVariant savedProductVariant = productVariantRepository.save(productVariant);
        // map ProductVariant to ProductVariantResponse
        return productVariantMapper.productVariantToProductVariantResponse(savedProductVariant);
    }
}
