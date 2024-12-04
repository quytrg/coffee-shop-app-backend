package com.project.coffeeshopapp.specifications;

import com.project.coffeeshopapp.models.Ingredient;
import org.springframework.data.jpa.domain.Specification;

public class IngredientSpecification {
    private Specification<Ingredient> spec;

    public static IngredientSpecification builder() {
        return new IngredientSpecification();
    }

    private IngredientSpecification() {
        this.spec = Specification.where(null);
    }

    public IngredientSpecification keyword(String keyword) {
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

    public Specification<Ingredient> build() {
        return spec;
    }
}
