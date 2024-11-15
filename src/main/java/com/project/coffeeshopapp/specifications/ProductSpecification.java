package com.project.coffeeshopapp.specifications;

import com.project.coffeeshopapp.enums.ProductStatus;
import com.project.coffeeshopapp.models.Product;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

public class ProductSpecification {

    private Specification<Product> spec;

    public static ProductSpecification builder() {
        return new ProductSpecification();
    }

    private ProductSpecification() {
        this.spec = Specification.where(null);
    }

    public ProductSpecification categoryIds(List<Long> categoryIds) {
        if (categoryIds != null && !categoryIds.isEmpty()) {
            spec = spec.and((root, query, criteriaBuilder) ->
                    root.get("category").get("id").in(categoryIds)
            );
        }
        return this;
    }

    public ProductSpecification keyword(String keyword) {
        if (keyword != null && !keyword.trim().isEmpty()) {
            String likePattern = "%" + keyword.toLowerCase() + "%";
            spec = spec.and((root, query, criteriaBuilder) ->
                    criteriaBuilder.or(
                            criteriaBuilder.like(criteriaBuilder.lower(root.get("name")), likePattern),
                            criteriaBuilder.like(criteriaBuilder.lower(root.get("category").get("name")), likePattern),
                            criteriaBuilder.like(criteriaBuilder.lower(root.get("description")), likePattern)
                    )
            );
        }
        return this;
    }

    public ProductSpecification status(ProductStatus status) {
        if (status != null) {
            spec = spec.and((root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("status"), status)
            );
        }
        return this;
    }

    public ProductSpecification position(Long minPosition, Long maxPosition) {
        // Minimum position filter
        if (minPosition != null) {
            spec = spec.and((root, query, criteriaBuilder) ->
                    criteriaBuilder.greaterThanOrEqualTo(root.get("position"), minPosition)
            );
        }
        // Maximum position filter
        if (maxPosition != null) {
            spec = spec.and((root, query, criteriaBuilder) ->
                    criteriaBuilder.lessThanOrEqualTo(root.get("position"), maxPosition)
            );
        }

        return this;
    }

    public Specification<Product> build() {
        return spec;
    }
}
