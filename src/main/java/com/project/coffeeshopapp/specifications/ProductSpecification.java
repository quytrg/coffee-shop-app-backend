package com.project.coffeeshopapp.specifications;

import com.project.coffeeshopapp.enums.ProductStatus;
import com.project.coffeeshopapp.models.Product;
import org.springframework.data.jpa.domain.Specification;

public class ProductSpecification {

    private Specification<Product> spec;

    public static ProductSpecification builder() {
        return new ProductSpecification();
    }

    private ProductSpecification() {
        this.spec = Specification.where(null);
    }

    public ProductSpecification categoryId(Long categoryId) {
        if (categoryId != null) {
            spec = spec.and((root, query, criteriaBuilder) -> {
                // Perform a fetch join to eagerly load Category
                return criteriaBuilder.equal(root.get("category").get("id"), categoryId);
            });
        }
        return this;
    }

    public ProductSpecification keyword(String keyword) {
        if (keyword != null && !keyword.trim().isEmpty()) {
            String likePattern = "%" + keyword.toLowerCase() + "%";
            spec = spec.and((root, query, criteriaBuilder) ->
                    criteriaBuilder.or(
                            criteriaBuilder.like(criteriaBuilder.lower(root.get("name")), likePattern),
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

    public Specification<Product> build() {
        return spec;
    }
}
