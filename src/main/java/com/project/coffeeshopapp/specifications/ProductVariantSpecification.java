package com.project.coffeeshopapp.specifications;

import com.project.coffeeshopapp.enums.ProductVariantStatus;
import com.project.coffeeshopapp.models.ProductVariant;
import org.springframework.data.jpa.domain.Specification;

public class ProductVariantSpecification {
    private Specification<ProductVariant> spec;

    public static ProductVariantSpecification builder() {
        return new ProductVariantSpecification();
    }

    private ProductVariantSpecification() {
        this.spec = Specification.where(null);
    }

    public ProductVariantSpecification productId(Long productId) {
        if (productId != null) {
            spec = spec.and((root, query, criteriaBuilder) ->
                    criteriaBuilder.equal(root.get("product").get("id"), productId)
            );
        }
        return this;
    }

    public ProductVariantSpecification keyword(String keyword) {
        if (keyword != null && !keyword.trim().isEmpty()) {
            String likePattern = "%" + keyword.toLowerCase() + "%";
            spec = spec.and((root, query, criteriaBuilder) ->
                    criteriaBuilder.or(
                            criteriaBuilder.like(criteriaBuilder.lower(root.get("description")), likePattern)
                    )
            );
        }
        return this;
    }

    public ProductVariantSpecification status(ProductVariantStatus status) {
        if (status != null) {
            spec = spec.and((root, query, criteriaBuilder) ->
                    criteriaBuilder.equal(root.get("status"), status)
            );
        }
        return this;
    }

    public Specification<ProductVariant> build() {
        return spec;
    }
}
