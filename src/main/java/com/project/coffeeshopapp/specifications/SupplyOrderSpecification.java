package com.project.coffeeshopapp.specifications;

import com.project.coffeeshopapp.enums.PaymentMethod;
import com.project.coffeeshopapp.enums.PaymentStatus;
import com.project.coffeeshopapp.enums.SupplyOrderStatus;
import com.project.coffeeshopapp.models.SupplyOrder;
import com.project.coffeeshopapp.models.SupplyOrderItem;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import org.springframework.data.jpa.domain.Specification;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public class SupplyOrderSpecification {
    private Specification<SupplyOrder> spec;

    public static SupplyOrderSpecification builder() {
        return new SupplyOrderSpecification();
    }

    private SupplyOrderSpecification() {
        this.spec = Specification.where(null);
    }

    public SupplyOrderSpecification keyword(String keyword) {
        if (keyword != null && !keyword.trim().isEmpty()) {
            String likePattern = "%" + keyword.trim().toLowerCase() + "%";
            spec = spec.and((root, query, criteriaBuilder) ->
                    criteriaBuilder.or(
                            criteriaBuilder.like(criteriaBuilder.lower(root.get("orderCode")), likePattern),
                            criteriaBuilder.like(criteriaBuilder.lower(root.get("description")), likePattern)
                    )
            );
        }
        return this;
    }

    public SupplyOrderSpecification totalAmount(BigDecimal minTotalAmount, BigDecimal maxTotalAmount) {
        // Minimum totalAmount filter
        if (minTotalAmount != null) {
            spec = spec.and((root, query, criteriaBuilder) ->
                    criteriaBuilder.greaterThanOrEqualTo(root.get("totalAmount"), minTotalAmount)
            );
        }
        // Maximum totalAmount filter
        if (maxTotalAmount != null) {
            spec = spec.and((root, query, criteriaBuilder) ->
                    criteriaBuilder.lessThanOrEqualTo(root.get("totalAmount"), maxTotalAmount)
            );
        }

        return this;
    }

    public SupplyOrderSpecification status(SupplyOrderStatus status) {
        if (status != null) {
            spec = spec.and((root, query, criteriaBuilder) ->
                    criteriaBuilder.equal(root.get("status"), status)
            );
        }
        return this;
    }

    public SupplyOrderSpecification paymentMethod(PaymentMethod paymentMethod) {
        if (paymentMethod != null) {
            spec = spec.and((root, query, criteriaBuilder) ->
                    criteriaBuilder.equal(root.get("paymentMethod"), paymentMethod)
            );
        }
        return this;
    }

    public SupplyOrderSpecification paymentStatus(PaymentStatus paymentStatus) {
        if (paymentStatus != null) {
            spec = spec.and((root, query, criteriaBuilder) ->
                    criteriaBuilder.equal(root.get("paymentStatus"), paymentStatus)
            );
        }
        return this;
    }

    public SupplyOrderSpecification expectedDeliveryDate(LocalDateTime from, LocalDateTime to) {
        if (from != null) {
            spec = spec.and((root, query, criteriaBuilder) ->
                    criteriaBuilder.greaterThanOrEqualTo(root.get("expectedDeliveryDate"), from)
            );
        }
        if (to != null) {
            spec = spec.and((root, query, criteriaBuilder) ->
                    criteriaBuilder.lessThanOrEqualTo(root.get("expectedDeliveryDate"), to)
            );
        }
        return this;
    }

    public SupplyOrderSpecification actualDeliveryDate(LocalDateTime from, LocalDateTime to) {
        if (from != null) {
            spec = spec.and((root, query, criteriaBuilder) ->
                    criteriaBuilder.greaterThanOrEqualTo(root.get("actualDeliveryDate"), from)
            );
        }
        if (to != null) {
            spec = spec.and((root, query, criteriaBuilder) ->
                    criteriaBuilder.lessThanOrEqualTo(root.get("actualDeliveryDate"), to)
            );
        }
        return this;
    }

    public SupplyOrderSpecification supplierIds(List<Long> supplierIds) {
        if (supplierIds != null && !supplierIds.isEmpty()) {
            spec = spec.and((root, query, criteriaBuilder) ->
                    root.get("supplier").get("id").in(supplierIds)
            );
        }
        return this;
    }

    public SupplyOrderSpecification ingredientIds(List<Long> ingredientIds) {
        if (ingredientIds != null && !ingredientIds.isEmpty()) {
            spec = spec.and((root, query, criteriaBuilder) -> {
                    query.distinct(true);
                    Join<SupplyOrder, SupplyOrderItem> itemsJoin = root.join("supplyOrderItems", JoinType.INNER);
                    return itemsJoin.get("ingredient").get("id").in(ingredientIds);
            });
        }
        return this;
    }

    public Specification<SupplyOrder> build() {
        return spec;
    }
}
