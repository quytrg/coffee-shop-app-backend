package com.project.coffeeshopapp.specifications;

import com.project.coffeeshopapp.enums.RoleStatus;
import com.project.coffeeshopapp.models.Permission;
import com.project.coffeeshopapp.models.Role;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

public class RoleSpecification {
    private Specification<Role> spec;

    public static RoleSpecification builder() {
        return new RoleSpecification();
    }

    private RoleSpecification() {
        this.spec = Specification.where(null);
    }

    public RoleSpecification permissionIds(List<Long> permissionIds) {
        if (permissionIds != null && !permissionIds.isEmpty()) {
            spec = spec.and((root, query, criteriaBuilder) -> {
                query.distinct(true);
                Join<Role, Permission> permissionsJoin  = root.join("permissions", JoinType.INNER);
                return permissionsJoin .get("id").in(permissionIds);
            });
        }
        return this;
    }

    public RoleSpecification keyword(String keyword) {
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

    public RoleSpecification status(RoleStatus status) {
        if (status != null) {
            spec = spec.and((root, query, criteriaBuilder) ->
                    criteriaBuilder.equal(root.get("status"), status)
            );
        }
        return this;
    }

    public Specification<Role> build() {
        return spec;
    }
}
