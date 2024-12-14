package com.project.coffeeshopapp.specifications;

import com.project.coffeeshopapp.enums.CategoryStatus;
import com.project.coffeeshopapp.models.Category;
import org.springframework.data.jpa.domain.Specification;

public class CategorySpecification {
    private Specification<Category> spec;

    public static CategorySpecification builder() {
        return new CategorySpecification();
    }

    private CategorySpecification() {
        this.spec = Specification.where(null);
    }

    public CategorySpecification keyword(String keyword) {
        if (keyword != null && !keyword.trim().isEmpty()) {
            String likePattern = "%" + keyword.trim().toLowerCase() + "%";
            spec = spec.and((root, query, criteriaBuilder) ->
                    criteriaBuilder.or(
                            criteriaBuilder.like(criteriaBuilder.lower(root.get("name")), likePattern),
                            criteriaBuilder.like(criteriaBuilder.lower(root.get("description")), likePattern)
                    )
            );
        }
        return this;
    }

    public CategorySpecification status(CategoryStatus status) {
        if (status != null) {
            spec = spec.and((root, query, criteriaBuilder) ->
                    criteriaBuilder.equal(root.get("status"), status)
            );
        }
        return this;
    }

    public Specification<Category> build() {
        return spec;
    }
}
