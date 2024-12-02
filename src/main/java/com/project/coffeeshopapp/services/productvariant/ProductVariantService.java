package com.project.coffeeshopapp.services.productvariant;

import com.project.coffeeshopapp.customexceptions.DataNotFoundException;
import com.project.coffeeshopapp.customexceptions.UnitMismatchException;
import com.project.coffeeshopapp.dtos.request.productvariant.ProductVariantCreateRequest;
import com.project.coffeeshopapp.dtos.request.productvariant.ProductVariantSearchRequest;
import com.project.coffeeshopapp.dtos.request.productvariant.ProductVariantUpdateRequest;
import com.project.coffeeshopapp.dtos.request.productvariantingredient.ProductVariantIngredientRequest;
import com.project.coffeeshopapp.dtos.response.productvariant.ProductVariantResponse;
import com.project.coffeeshopapp.dtos.response.productvariant.ProductVariantSummaryResponse;
import com.project.coffeeshopapp.mappers.ProductVariantIngredientMapper;
import com.project.coffeeshopapp.mappers.ProductVariantMapper;
import com.project.coffeeshopapp.models.Ingredient;
import com.project.coffeeshopapp.models.Product;
import com.project.coffeeshopapp.models.ProductVariant;
import com.project.coffeeshopapp.models.ProductVariantIngredient;
import com.project.coffeeshopapp.repositories.IngredientRepository;
import com.project.coffeeshopapp.repositories.ProductRepository;
import com.project.coffeeshopapp.repositories.ProductVariantRepository;
import com.project.coffeeshopapp.specifications.ProductVariantSpecification;
import com.project.coffeeshopapp.utils.PaginationUtil;
import com.project.coffeeshopapp.utils.SortUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductVariantService implements IProductVariantService {
    private final ProductVariantMapper productVariantMapper;
    private final ProductVariantRepository productVariantRepository;
    private final PaginationUtil paginationUtil;
    private final SortUtil sortUtil;
    private final ProductRepository productRepository;
    private final IngredientRepository ingredientRepository;
    private final ProductVariantIngredientMapper productVariantIngredientMapper;

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

        // set ingredients
        List<ProductVariantIngredient> pvIngredients = processToProductVariantIngredients(
                productVariantCreateRequest.getIngredients()
        );
        productVariant.setIngredients(pvIngredients);

        // create product variant
        ProductVariant savedProductVariant = productVariantRepository.save(productVariant);
        // map ProductVariant to ProductVariantResponse
        return productVariantMapper.productVariantToProductVariantResponse(savedProductVariant);
    }

    @Override
    @Transactional
    public ProductVariantResponse updateProductVariant(
            Long productId,
            Long variantId,
            ProductVariantUpdateRequest productVariantUpdateRequest) {
        ProductVariant productVariant = productVariantRepository.findByIdAndProductId(variantId, productId)
                .orElseThrow(() -> new DataNotFoundException("ProductVariant", "ProductVariant not found with id: " + variantId + " for Product id: " + productId));
        // map fields not null from ProductVariantUpdateRequest to ProductVariant
        productVariantMapper.productVariantUpdateRequestToProductVariant(productVariantUpdateRequest, productVariant);
        // set a new ingredients if ingredients not null
        Optional.ofNullable(productVariantUpdateRequest.getIngredients())
                .ifPresent(ingredients -> {
                    productVariant.setIngredients(processToProductVariantIngredients(ingredients));
                });
        // save update
        ProductVariant updatedVariant = productVariantRepository.save(productVariant);
        // map ProductVariant tp ProductVariantResponse
        return productVariantMapper.productVariantToProductVariantResponse(updatedVariant);
    }

    private List<ProductVariantIngredient> processToProductVariantIngredients(List<ProductVariantIngredientRequest> requests) {
        List<Long> ingredientIds = requests.stream()
                .map(ProductVariantIngredientRequest::getIngredientId)
                .toList();

        List<Ingredient> ingredients = ingredientRepository.findAllById(ingredientIds);

        // check if any ingredient id not found
        if (ingredients.size() != ingredientIds.size()) {
            Set<Long> foundIds = ingredients.stream()
                    .map(Ingredient::getId)
                    .collect(Collectors.toSet());
            List<Long> missingIds = ingredientIds.stream()
                    .filter(id -> !foundIds.contains(id))
                    .toList();
            throw new DataNotFoundException("ingredient", "Ingredients not found with ids: " + missingIds);
        }

        Map<Long, Ingredient> ingredientMap = ingredients.stream()
                .collect(Collectors.toMap(Ingredient::getId, Function.identity()));

        List<ProductVariantIngredient> pvIngredients = new ArrayList<>();

        for (ProductVariantIngredientRequest request : requests) {
            Ingredient ingredient = ingredientMap.get(request.getIngredientId());

            // verify that the unit of request matches the default unit of the ingredient
            if (!ingredient.getDefaultUnit().equals(request.getUnit())) {
                throw new UnitMismatchException("Unit mismatch: Expected unit "
                        + ingredient.getDefaultUnit() + " for ingredientId " + request.getIngredientId());
            }

            ProductVariantIngredient pvi = productVariantIngredientMapper
                    .productVariantIngredientRequestToProductVariantIngredient(request);
            pvi.setIngredient(ingredient);
            pvIngredients.add(pvi);
        }

        return pvIngredients;
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
        Specification<ProductVariant> specification = ProductVariantSpecification.builder()
                .productId(productId)
                .keyword(productVariantSearchRequest.getKeyword())
                .status(productVariantSearchRequest.getStatus())
                .build();
        Page<ProductVariant> productVariants = productVariantRepository.findAll(specification, pageable);
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
