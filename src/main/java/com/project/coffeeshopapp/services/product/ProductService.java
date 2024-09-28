package com.project.coffeeshopapp.services.product;

import com.project.coffeeshopapp.customexceptions.DataNotFoundException;
import com.project.coffeeshopapp.dtos.request.product.ProductCreateRequest;
import com.project.coffeeshopapp.dtos.request.product.ProductSearchRequest;
import com.project.coffeeshopapp.dtos.request.product.ProductUpdateRequest;
import com.project.coffeeshopapp.dtos.response.product.ProductResponse;
import com.project.coffeeshopapp.dtos.response.product.ProductSummaryResponse;
import com.project.coffeeshopapp.mappers.ProductMapper;
import com.project.coffeeshopapp.models.Category;
import com.project.coffeeshopapp.models.Image;
import com.project.coffeeshopapp.models.Product;
import com.project.coffeeshopapp.repositories.CategoryRepository;
import com.project.coffeeshopapp.repositories.ProductRepository;
import com.project.coffeeshopapp.services.image.IImageService;
import com.project.coffeeshopapp.specifications.ProductSpecification;
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
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService implements IProductService {
    private final ProductMapper productMapper;
    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;
    private final PaginationUtil paginationUtil;
    private final SortUtil sortUtil;
    private final IImageService imageService;

    @Override
    @Transactional
    public ProductResponse createProduct(ProductCreateRequest productCreateRequest) {
        // convert from ProductCreateRequest to Product
        Product product = productMapper.productCreateRequestToProduct(productCreateRequest);

        // check if categoryId exists
        Category category = categoryRepository.findById(productCreateRequest.getCategoryId())
                .orElseThrow(() -> new DataNotFoundException("category", "Category not found with id: " + productCreateRequest.getCategoryId()));
        // set category for product
        product.setCategory(category);

        // Associate images with product
        List<Image> images = imageService.associateImagesWithProduct(product, productCreateRequest.getImageIds());
        product.setImages(images);

        // create product
        Product newProduct = productRepository.save(product);
        // mapping new product to dto response
        return productMapper.productToProductResponse(newProduct);
    }

    @Override
    @Transactional
    public ProductResponse updateProduct(Long id, ProductUpdateRequest productUpdateRequest) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new DataNotFoundException("product", "Product not found with id: " + id));
        // mapping fields not null from ProductUpdateRequest to Product
        productMapper.productUpdateRequestToProduct(productUpdateRequest, product);
        // finding and mapping category if category id not null and exists
        Optional.ofNullable(productUpdateRequest.getCategoryId())
                .ifPresent(categoryId -> {
                    Category category = categoryRepository.findById(categoryId)
                            .orElseThrow(() -> new DataNotFoundException("category", "Category not found with id: " + categoryId));
                    product.setCategory(category);
                });
        // Handle image associations
        // imageIds always have number of items >= 1 or imageIds = null
        // because of size validation in productUpdateRequest
        Optional.ofNullable(productUpdateRequest.getImageIds())
                .ifPresent(imageIds -> {
                    // Convert lists to sets for efficient lookup
                    Set<Long> newImageIds = new HashSet<>(imageIds);
                    Set<Long> oldImageIds = product.getImages().stream()
                            .map(Image::getId)
                            .collect(Collectors.toSet());

                    // Determine images to add
                    Set<Long> imagesToAdd = new HashSet<>(newImageIds);
                    imagesToAdd.removeAll(oldImageIds);

                    // Determine images to remove
                    Set<Long> imagesToRemove = new HashSet<>(oldImageIds);
                    imagesToRemove.removeAll(newImageIds);

                    // Associate new images
                    if (!imagesToAdd.isEmpty()) {
                        List<Image> newImages = imageService.associateImagesWithProduct(product, new ArrayList<>(imagesToAdd));
                        product.getImages().addAll(newImages);
                    }

                    // Dissociate removed images
                    if (!imagesToRemove.isEmpty()) {
                        List<Image> images = product.getImages().stream()
                                .filter(image -> imagesToRemove.contains(image.getId()))
                                .toList();
                        product.getImages().removeAll(images);
                    }
                });
        Product updatedProduct = productRepository.save(product);
        return productMapper.productToProductResponse(updatedProduct);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ProductSummaryResponse> getAllProducts(ProductSearchRequest request) {
        Sort sort = sortUtil.createSort(
                request.getSortBy(),
                request.getSortDir()
        );
        Pageable pageable = paginationUtil.createPageable(
                request.getPage(),
                request.getSize(),
                sort
        );

        Specification<Product> spec = ProductSpecification.builder()
                .categoryId(request.getCategoryId())
                .keyword(request.getKeyword())
                .status(request.getStatus())
                .build();

        Page<Product> products = productRepository.findAll(spec, pageable);
        return products.map(productMapper::productToProductSummaryResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public ProductResponse getProductById(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new DataNotFoundException("product", "Product not found with id: " + id));
        return productMapper.productToProductResponse(product);
    }

    @Override
    @Transactional
    public void softDeleteProduct(Long id) {
        productRepository.findById(id)
                .orElseThrow(() -> new DataNotFoundException("product", "Product not found with id: " + id));
        productRepository.softDelete(id);
    }
}
