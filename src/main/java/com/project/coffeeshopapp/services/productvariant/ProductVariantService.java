package com.project.coffeeshopapp.services.productvariant;

import com.project.coffeeshopapp.customexceptions.DataNotFoundException;
import com.project.coffeeshopapp.dtos.request.productvariant.ProductVariantCreateRequest;
import com.project.coffeeshopapp.dtos.request.productvariant.ProductVariantSearchRequest;
import com.project.coffeeshopapp.dtos.request.productvariant.ProductVariantUpdateRequest;
import com.project.coffeeshopapp.dtos.response.productvariant.ProductVariantResponse;
import com.project.coffeeshopapp.dtos.response.productvariant.ProductVariantSummaryResponse;
import com.project.coffeeshopapp.mappers.ProductVariantMapper;
import com.project.coffeeshopapp.models.Product;
import com.project.coffeeshopapp.models.ProductVariant;
import com.project.coffeeshopapp.repositories.ProductRepository;
import com.project.coffeeshopapp.repositories.ProductVariantRepository;
import com.project.coffeeshopapp.utils.PaginationUtil;
import com.project.coffeeshopapp.utils.SortUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
        Product product = productRepository.findByIdWithCategory(productId)
                .orElseThrow(() -> new DataNotFoundException("product",
                        "Product not found with id: " + productId));
        // set product for product variant
        productVariant.setProduct(product);
        // create product variant
        ProductVariant savedProductVariant = productVariantRepository.save(productVariant);
        // map ProductVariant to ProductVariantResponse
        return productVariantMapper.productVariantToProductVariantResponse(savedProductVariant);
    }

    @Override
    @Transactional
    public ProductVariantResponse updateProductVariant(Long productId, Long variantId, ProductVariantUpdateRequest productVariantUpdateRequest) {
        ProductVariant productVariant = productVariantRepository.findByIdAndProductId(variantId, productId)
                .orElseThrow(() -> new DataNotFoundException("ProductVariant", "ProductVariant not found with id: " + variantId + " for Product id: " + productId));
        // map fields not null from ProductVariantUpdateRequest to ProductVariant
        productVariantMapper.productVariantUpdateRequestToProductVariant(productVariantUpdateRequest, productVariant);
        // save update
        ProductVariant updatedVariant = productVariantRepository.save(productVariant);
        // map ProductVariant tp ProductVariantResponse
        return productVariantMapper.productVariantToProductVariantResponse(updatedVariant);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ProductVariantSummaryResponse> getProductVariants(
            Long productId,
            ProductVariantSearchRequest productVariantSearchRequest) {
        Sort sort = sortUtil.createSort(
                productVariantSearchRequest.getSortBy(),
                productVariantSearchRequest.getSortDir()
        );
        Pageable pageable = paginationUtil.createPageable(
                productVariantSearchRequest.getPage(),
                productVariantSearchRequest.getSize(),
                sort
        );
        Page<ProductVariant> productVariants = productVariantRepository.findByProductId(productId, pageable);
        return productVariants.map(productVariantMapper::productVariantToProductVariantSummaryResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public ProductVariantResponse getProductVariant(Long productId, Long variantId) {
        ProductVariant productVariant = productVariantRepository.findByIdAndProductId(variantId, productId)
                .orElseThrow(() -> new DataNotFoundException(
                            "ProductVariant",
                            "ProductVariant not found with id: " + variantId + " for Product id: " + productId
                        )
                );
        return productVariantMapper.productVariantToProductVariantResponse(productVariant);
    }

    @Override
    @Transactional
    public void softDeleteProductVariant(Long productId, Long variantId) {
        productVariantRepository.findByIdAndProductId(variantId, productId)
                .orElseThrow(() -> new DataNotFoundException(
                                "ProductVariant",
                                "ProductVariant not found with id: " + variantId + " for Product id: " + productId
                        )
                );
        productVariantRepository.softDelete(variantId);
    }


}
