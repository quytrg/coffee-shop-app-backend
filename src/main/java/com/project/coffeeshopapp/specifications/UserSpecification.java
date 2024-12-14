package com.project.coffeeshopapp.specifications;

import com.project.coffeeshopapp.enums.UserStatus;
import com.project.coffeeshopapp.models.User;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

public class UserSpecification {
    private Specification<User> spec;

    public static UserSpecification builder() {
        return new UserSpecification();
    }

    private UserSpecification() {
        this.spec = Specification.where(null);
    }

    public UserSpecification roleId(Long roleId) {
        if (roleId != null) {
            spec = spec.and((root, query, criteriaBuilder) -> {
                // Perform a fetch join to eagerly load Category
                return criteriaBuilder.equal(root.get("role").get("id"), roleId);
            });
        }
        return this;
    }

    public UserSpecification keyword(String keyword) {
        if (keyword != null && !keyword.trim().isEmpty()) {
            String likePattern = "%" + keyword.trim().toLowerCase() + "%";
            spec = spec.and((root, query, criteriaBuilder) ->
                    criteriaBuilder.or(
                            criteriaBuilder.like(criteriaBuilder.lower(root.get("fullName")), likePattern),
                            criteriaBuilder.like(criteriaBuilder.lower(root.get("email")), likePattern),
                            criteriaBuilder.like(criteriaBuilder.lower(root.get("phoneNumber")), likePattern),
                            criteriaBuilder.like(criteriaBuilder.lower(root.get("address")), likePattern)
                    )
            );
        }
        return this;
    }

    public UserSpecification status(UserStatus status) {
        if (status != null) {
            spec = spec.and((root, query, criteriaBuilder) ->
                    criteriaBuilder.equal(root.get("status"), status)
            );
        }
        return this;
    }

    public UserSpecification sex(Boolean sex) {
        if (sex != null) {
            spec = spec.and((root, query, criteriaBuilder) ->
                    criteriaBuilder.equal(root.get("sex"), sex)
            );
        }
        return this;
    }

    public UserSpecification roleIds(List<Long> roleIds) {
        if (roleIds != null && !roleIds.isEmpty()) {
            spec = spec.and((root, query, criteriaBuilder) ->
                    root.get("role").get("id").in(roleIds)
            );
        }
        return this;
    }

    public Specification<User> build() {
        return spec;
    }
}
