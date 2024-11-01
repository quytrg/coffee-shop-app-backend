package com.project.coffeeshopapp.specifications;

import com.project.coffeeshopapp.enums.OrderStatus;
import com.project.coffeeshopapp.enums.PaymentMethod;
import com.project.coffeeshopapp.models.Order;
import org.springframework.data.jpa.domain.Specification;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public class OrderSpecification {

    private Specification<Order> spec;

    public static OrderSpecification builder() {
        return new OrderSpecification();
    }

    private OrderSpecification() {
        this.spec = Specification.where(null);
    }

    public OrderSpecification keyword(String keyword) {
        if (keyword != null && !keyword.trim().isEmpty()) {
            String likePattern = "%" + keyword.toLowerCase() + "%";
            spec = spec.and((root, query, criteriaBuilder) ->
                    criteriaBuilder.or(
                            criteriaBuilder.like(criteriaBuilder.lower(root.get("customerName")), likePattern),
                            criteriaBuilder.like(criteriaBuilder.lower(root.get("orderCode")), likePattern),
                            criteriaBuilder.like(criteriaBuilder.lower(root.get("description")), likePattern)
                    )
            );
        }
        return this;
    }

    public OrderSpecification status(OrderStatus status) {
        if (status != null) {
            spec = spec.and((root, query, criteriaBuilder) ->
                    criteriaBuilder.equal(root.get("status"), status)
            );
        }
        return this;
    }

    public OrderSpecification totalAmount(BigDecimal minTotalAmount, BigDecimal maxTotalAmount) {
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

    public OrderSpecification paymentMethod(PaymentMethod paymentMethod) {
        if (paymentMethod != null) {
            spec = spec.and((root, query, criteriaBuilder) ->
                    criteriaBuilder.equal(root.get("paymentMethod"), paymentMethod)
            );
        }
        return this;
    }

    public OrderSpecification receivedAmount(BigDecimal minReceivedAmount, BigDecimal maxReceivedAmount) {
        // Minimum minReceivedAmount filter
        if (minReceivedAmount != null) {
            spec = spec.and((root, query, criteriaBuilder) ->
                    criteriaBuilder.greaterThanOrEqualTo(root.get("receivedAmount"), minReceivedAmount)
            );
        }
        // Maximum maxReceivedAmount filter
        if (maxReceivedAmount != null) {
            spec = spec.and((root, query, criteriaBuilder) ->
                    criteriaBuilder.lessThanOrEqualTo(root.get("receivedAmount"), maxReceivedAmount)
            );
        }

        return this;
    }

    public OrderSpecification returnAmount(BigDecimal minReturnAmount, BigDecimal maxReturnAmount) {
        // Minimum minReturnAmount filter
        if (minReturnAmount != null) {
            spec = spec.and((root, query, criteriaBuilder) ->
                    criteriaBuilder.greaterThanOrEqualTo(root.get("returnAmount"), minReturnAmount)
            );
        }
        // Maximum maxReturnAmount filter
        if (maxReturnAmount != null) {
            spec = spec.and((root, query, criteriaBuilder) ->
                    criteriaBuilder.lessThanOrEqualTo(root.get("returnAmount"), maxReturnAmount)
            );
        }

        return this;
    }

    public OrderSpecification userIds(List<Long> userIds) {
        if (userIds != null && !userIds.isEmpty()) {
            spec = spec.and((root, query, criteriaBuilder) ->
                    root.get("user").get("id").in(userIds)
            );
        }
        return this;
    }

    public OrderSpecification createdAt(LocalDateTime from, LocalDateTime to) {
        if (from != null) {
            spec = spec.and((root, query, criteriaBuilder) ->
                    criteriaBuilder.greaterThanOrEqualTo(root.get("createdAt"), from)
            );
        }
        if (to != null) {
            spec = spec.and((root, query, criteriaBuilder) ->
                    criteriaBuilder.lessThanOrEqualTo(root.get("createdAt"), to)
            );
        }
        return this;
    }

    public OrderSpecification updatedAt(LocalDateTime from, LocalDateTime to) {
        if (from != null) {
            spec = spec.and((root, query, criteriaBuilder) ->
                    criteriaBuilder.greaterThanOrEqualTo(root.get("updatedAt"), from)
            );
        }
        if (to != null) {
            spec = spec.and((root, query, criteriaBuilder) ->
                    criteriaBuilder.lessThanOrEqualTo(root.get("updatedAt"), to)
            );
        }
        return this;
    }

    public Specification<Order> build() {
        return spec;
    }
}
