package com.project.coffeeshopapp.customrepositories.impl;

import com.project.coffeeshopapp.customrepositories.OrderItemRepositoryCustom;
import com.project.coffeeshopapp.dtos.projection.OrderItemReport;
import com.project.coffeeshopapp.dtos.request.orderitem.OrderItemReportSearchRequest;
import com.project.coffeeshopapp.models.OrderItem;
import com.project.coffeeshopapp.models.Product;
import com.project.coffeeshopapp.models.ProductVariant;
import com.project.coffeeshopapp.models.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class OrderItemRepositoryCustomImpl implements OrderItemRepositoryCustom {
    private final EntityManager entityManager;

    @Override
    public Page<OrderItemReport> findAllForReport(OrderItemReportSearchRequest request, Pageable pageable) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();

        // Build the query
        CriteriaQuery<OrderItemReport> query = cb.createQuery(OrderItemReport.class);
        Root<OrderItem> root = query.from(OrderItem.class);

        // Perform the necessary JOINs once
        Join<OrderItem, Order> orderJoin = root.join("order", JoinType.INNER);
        Join<Object, User> userJoin = orderJoin.join("user", JoinType.INNER);
        Join<OrderItem, ProductVariant> productVariantJoin = root.join("productVariant", JoinType.INNER);
        Join<Object, Product> productJoin = productVariantJoin.join("product", JoinType.INNER);

        // Building Predicates for Filter Conditions
        List<Predicate> predicates = buildPredicates(
                cb,
                root,
                orderJoin,
                userJoin,
                productVariantJoin,
                productJoin,
                request
        );

        // Apply Predicates to Query
        query.where(cb.and(predicates.toArray(new Predicate[0])));

        // Sort handling
        Sort sort = pageable.getSort();
        if (sort.isSorted()) {
            List<Order> orders = buildOrderItemReportOrder(
                    cb,
                    sort,
                    root,
                    orderJoin,
                    productVariantJoin,
                    productJoin
            );
            query.orderBy(orders);
        }

        // Select the required fields for the DTO
        query.select(cb.construct(
                OrderItemReport.class,
                root.get("id"),
                productVariantJoin.get("id"),
                productJoin.get("id"),
                productJoin.get("name"),
                productVariantJoin.get("size"),
                root.get("description"),
                root.get("price"),
                root.get("quantity"),
                root.get("discount"),
                root.get("subtotal"),
                orderJoin.get("id"),
                orderJoin.get("orderCode"),
                orderJoin.get("status"),
                root.get("createdBy"),
                root.get("createdAt"),
                root.get("updatedBy"),
                root.get("updatedAt")
        ));

        // Create TypedQuery and execute
        TypedQuery<OrderItemReport> typedQuery = entityManager.createQuery(query);
        typedQuery.setFirstResult((int) pageable.getOffset());
        typedQuery.setMaxResults(pageable.getPageSize());

        List<OrderItemReport> resultList = typedQuery.getResultList();

        // Create Count Query to calculate total number of records
        Long total = getCount(cb, request);

        return new PageImpl<>(resultList, pageable, total);
    }

    /**
     * Build a Predicate list based on filter conditions.
     */
    private List<Predicate> buildPredicates(CriteriaBuilder cb,
                                            Root<OrderItem> root,
                                            Join<OrderItem, Order> orderJoin,
                                            Join<Object, User> userJoin,
                                            Join<OrderItem, ProductVariant> productVariantJoin,
                                            Join<Object, Product> productJoin,
                                            OrderItemReportSearchRequest request) {
        List<Predicate> predicates = new ArrayList<>();

        // keyword
        if (request.getKeyword() != null && !request.getKeyword().trim().isEmpty()) {
            String likePattern = "%" + request.getKeyword().toLowerCase() + "%";
            Predicate productNameLike = cb.like(cb.lower(productJoin.get("name")), likePattern);
            Predicate orderItemDescriptionLike = cb.like(cb.lower(root.get("description")), likePattern);
            Predicate orderCodeLike = cb.like(cb.lower(orderJoin.get("orderCode")), likePattern);
            predicates.add(cb.or(productNameLike, orderItemDescriptionLike, orderCodeLike));
        }

        // productVariantIds
        if (request.getProductVariantIds() != null && !request.getProductVariantIds().isEmpty()) {
            predicates.add(productVariantJoin.get("id").in(request.getProductVariantIds()));
        }

        // productIds
        if (request.getProductIds() != null && !request.getProductIds().isEmpty()) {
            predicates.add(productJoin.get("id").in(request.getProductIds()));
        }

        // productVariantSize
        if (request.getProductVariantSize() != null) {
            predicates.add(cb.equal(productVariantJoin.get("size"), request.getProductVariantSize()));
        }

        // price
        if (request.getMinPrice() != null) {
            predicates.add(cb.greaterThanOrEqualTo(root.get("price"), request.getMinPrice()));
        }
        if (request.getMaxPrice() != null) {
            predicates.add(cb.lessThanOrEqualTo(root.get("price"), request.getMaxPrice()));
        }

        // quantity
        if (request.getMinQuantity() != null) {
            predicates.add(cb.greaterThanOrEqualTo(root.get("quantity"), request.getMinQuantity()));
        }
        if (request.getMaxQuantity() != null) {
            predicates.add(cb.lessThanOrEqualTo(root.get("quantity"), request.getMaxQuantity()));
        }

        // discount
        if (request.getMinDiscount() != null) {
            predicates.add(cb.greaterThanOrEqualTo(root.get("discount"), request.getMinDiscount()));
        }
        if (request.getMaxDiscount() != null) {
            predicates.add(cb.lessThanOrEqualTo(root.get("discount"), request.getMaxDiscount()));
        }

        // subtotal
        if (request.getMinSubtotal() != null) {
            predicates.add(cb.greaterThanOrEqualTo(root.get("subtotal"), request.getMinSubtotal()));
        }
        if (request.getMaxSubtotal() != null) {
            predicates.add(cb.lessThanOrEqualTo(root.get("subtotal"), request.getMaxSubtotal()));
        }

        // orderIds
        if (request.getOrderIds() != null && !request.getOrderIds().isEmpty()) {
            predicates.add(orderJoin.get("id").in(request.getOrderIds()));
        }

        // orderCodes
        if (request.getOrderCodes() != null && !request.getOrderCodes().isEmpty()) {
            predicates.add(orderJoin.get("orderCode").in(request.getOrderCodes()));
        }

        // orderStatus
        if (request.getOrderStatus() != null) {
            predicates.add(cb.equal(orderJoin.get("status"), request.getOrderStatus()));
        }

        // createdAt
        if (request.getCreatedAtFrom() != null) {
            predicates.add(cb.greaterThanOrEqualTo(root.get("createdAt"), request.getCreatedAtFrom()));
        }
        if (request.getCreatedAtTo() != null) {
            predicates.add(cb.lessThanOrEqualTo(root.get("createdAt"), request.getCreatedAtTo()));
        }

        // updatedAt
        if (request.getUpdatedAtFrom() != null) {
            predicates.add(cb.greaterThanOrEqualTo(root.get("updatedAt"), request.getUpdatedAtFrom()));
        }
        if (request.getUpdatedAtTo() != null) {
            predicates.add(cb.lessThanOrEqualTo(root.get("updatedAt"), request.getUpdatedAtTo()));
        }

        // createdByIds
        if (request.getCreatedByIds() != null && !request.getCreatedByIds().isEmpty()) {
            predicates.add(userJoin.get("id").in(request.getCreatedByIds()));
        }

        // updatedByIds
        if (request.getUpdatedByIds() != null && !request.getUpdatedByIds().isEmpty()) {
            predicates.add(userJoin.get("id").in(request.getUpdatedByIds()));
        }

        return predicates;
    }

    /**
     * Build Order list based on Sort.
     */
    private List<Order> buildOrderItemReportOrder(CriteriaBuilder cb,
                                             Sort sort,
                                             Root<OrderItem> root,
                                             Join<OrderItem, Order> orderJoin,
                                             Join<OrderItem, ProductVariant> productVariantJoin,
                                             Join<Object, Product> productJoin) {
        List<Order> orders = new ArrayList<>();
        sort.forEach(order -> {
            String property = order.getProperty();
            Path<?> path = switch (property) {
                case "productName" -> productJoin.get("name");
                case "productVariantSize" -> productVariantJoin.get("size");
                case "orderCode" -> orderJoin.get("orderCode");
                case "orderStatus" -> orderJoin.get("status");
                default -> root.get(property);
            };
            orders.add(order.isAscending() ? cb.asc(path) : cb.desc(path));
        });
        return orders;
    }

    /**
     * Create a Count Query to calculate the total number of records that satisfy the filter conditions.
     */
    private Long getCount(CriteriaBuilder cb, OrderItemReportSearchRequest request) {
        CriteriaQuery<Long> countQuery = cb.createQuery(Long.class);
        Root<OrderItem> countRoot = countQuery.from(OrderItem.class);

        // Perform JOINs in countQuery
        Join<OrderItem, Order> countOrderJoin = countRoot.join("order", JoinType.INNER);
        Join<Object, User> countUserJoin = countOrderJoin.join("user", JoinType.INNER);
        Join<OrderItem, ProductVariant> countProductVariantJoin = countRoot.join("productVariant", JoinType.INNER);
        Join<Object, Product> countProductJoin = countProductVariantJoin.join("product", JoinType.INNER);

        // Building Predicates for countQuery
        List<Predicate> countPredicates = buildPredicates(
                cb,
                countRoot,
                countOrderJoin,
                countUserJoin,
                countProductVariantJoin,
                countProductJoin,
                request
        );

        countQuery.select(cb.countDistinct(countRoot));
        countQuery.where(cb.and(countPredicates.toArray(new Predicate[0])));

        return entityManager.createQuery(countQuery).getSingleResult();
    }
}
