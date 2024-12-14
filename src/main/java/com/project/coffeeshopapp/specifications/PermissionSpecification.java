package com.project.coffeeshopapp.specifications;

import com.project.coffeeshopapp.models.Permission;
import org.springframework.data.jpa.domain.Specification;

public class PermissionSpecification {
    private Specification<Permission> spec;

    public static PermissionSpecification builder() {
        return new PermissionSpecification();
    }

    private PermissionSpecification() {
        this.spec = Specification.where(null);
    }

    public PermissionSpecification keyword(String keyword) {
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

    public Specification<Permission> build() {
        return spec;
    }
}
