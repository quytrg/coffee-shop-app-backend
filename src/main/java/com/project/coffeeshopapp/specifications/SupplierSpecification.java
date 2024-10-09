package com.project.coffeeshopapp.specifications;

import com.project.coffeeshopapp.models.Supplier;
import org.springframework.data.jpa.domain.Specification;

public class SupplierSpecification {
    private Specification<Supplier> spec;

    public static SupplierSpecification builder() {
        return new SupplierSpecification();
    }

    private SupplierSpecification() {
        this.spec = Specification.where(null);
    }

    public SupplierSpecification keyword(String keyword) {
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

    public SupplierSpecification rating(Double minRating, Double maxRating) {
        // Minimum rating filter
        if (minRating != null) {
            spec = spec.and((root, query, criteriaBuilder) ->
                    criteriaBuilder.greaterThanOrEqualTo(root.get("rating"), minRating)
            );
        }
        // Maximum rating filter
        if (maxRating != null) {
            spec = spec.and((root, query, criteriaBuilder) ->
                    criteriaBuilder.lessThanOrEqualTo(root.get("rating"), maxRating)
            );
        }

        return this;
    }

    public Specification<Supplier> build() {
        return spec;
    }
}
