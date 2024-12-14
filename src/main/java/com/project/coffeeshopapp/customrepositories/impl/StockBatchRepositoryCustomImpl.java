package com.project.coffeeshopapp.customrepositories.impl;

import com.project.coffeeshopapp.customrepositories.StockBatchRepositoryCustom;
import com.project.coffeeshopapp.dtos.projection.StockBatchReport;
import com.project.coffeeshopapp.dtos.projection.StockBatchSummary;
import com.project.coffeeshopapp.dtos.request.stockbatch.StockBatchReportSearchRequest;
import com.project.coffeeshopapp.dtos.request.stockbatch.StockBatchSearchRequest;
import com.project.coffeeshopapp.mappers.StockBatchMapper;
import com.project.coffeeshopapp.models.StockBatch;
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
public class StockBatchRepositoryCustomImpl implements StockBatchRepositoryCustom {
    private final EntityManager entityManager;
    private final StockBatchMapper stockBatchMapper;

    @Override
    public Page<StockBatchSummary> findAllWithFiltersAndSort(StockBatchSearchRequest request,
                                                                     Pageable pageable) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();

        // Build the query
        CriteriaQuery<StockBatchSummary> query = cb.createQuery(StockBatchSummary.class);
        Root<StockBatch> root = query.from(StockBatch.class);

        // Perform the necessary JOINs once
        Join<StockBatch, Object> supplyOrderItemJoin = root.join("supplyOrderItem", JoinType.INNER);
        Join<Object, Object> supplyOrderJoin = supplyOrderItemJoin.join("supplyOrder", JoinType.INNER);
        Join<Object, Object> supplierJoin = supplyOrderJoin.join("supplier", JoinType.INNER);
        Join<StockBatch, Object> ingredientJoin = root.join("ingredient", JoinType.INNER);

        // Building Predicates for Filter Conditions
        List<Predicate> predicates = buildPredicates(
                cb,
                root,
                supplyOrderItemJoin,
                supplyOrderJoin,
                supplierJoin,
                ingredientJoin,
                request
        );

        // Apply Predicates to Query
        query.where(cb.and(predicates.toArray(new Predicate[0])));

        // Sort handling
        Sort sort = pageable.getSort();
        if (sort.isSorted()) {
            List<Order> orders = buildStockBatchOrder(
                    cb,
                    sort,
                    root,
                    supplyOrderItemJoin,
                    supplyOrderJoin,
                    supplierJoin,
                    ingredientJoin
            );
            query.orderBy(orders);
        }

        // Select the required fields for the DTO
        query.select(cb.construct(
                StockBatchSummary.class,
                root.get("id"),
                ingredientJoin.get("id"),
                ingredientJoin.get("name"),
                ingredientJoin.get("defaultUnit"),
                root.get("initialQuantity"),
                root.get("remainingQuantity"),
                supplyOrderJoin.get("id"),
                supplyOrderJoin.get("orderCode"),
                supplyOrderJoin.get("actualDeliveryDate"),
                supplyOrderItemJoin.get("expirationDate"),
                supplierJoin.get("id"),
                supplierJoin.get("name")
        ));

        // Create TypedQuery and execute
        TypedQuery<StockBatchSummary> typedQuery = entityManager.createQuery(query);
        typedQuery.setFirstResult((int) pageable.getOffset());
        typedQuery.setMaxResults(pageable.getPageSize());

        List<StockBatchSummary> resultList = typedQuery.getResultList();

        // Create Count Query to calculate total number of records
        Long total = getCount(cb, request);

        return new PageImpl<>(resultList, pageable, total);
    }

    /**
     * Build a Predicate list based on filter conditions.
     */
    private List<Predicate> buildPredicates(CriteriaBuilder cb,
                                            Root<StockBatch> root,
                                            Join<StockBatch, Object> supplyOrderItemJoin,
                                            Join<Object, Object> supplyOrderJoin,
                                            Join<Object, Object> supplierJoin,
                                            Join<StockBatch, Object> ingredientJoin,
                                            StockBatchSearchRequest request) {
        List<Predicate> predicates = new ArrayList<>();

        // keyword
        if (request.getKeyword() != null && !request.getKeyword().trim().isEmpty()) {
            String likePattern = "%" + request.getKeyword().trim().toLowerCase() + "%";
            Predicate ingredientNameLike = cb.like(cb.lower(ingredientJoin.get("name")), likePattern);
            Predicate supplierNameLike = cb.like(cb.lower(supplierJoin.get("name")), likePattern);
            Predicate orderCodeLike = cb.like(cb.lower(supplyOrderJoin.get("orderCode")), likePattern);
            predicates.add(cb.or(ingredientNameLike, supplierNameLike, orderCodeLike));
        }

        // defaultUnit
        if (request.getDefaultUnit() != null) {
            predicates.add(cb.equal(ingredientJoin.get("defaultUnit"), request.getDefaultUnit()));
        }

        // initialQuantity
        if (request.getMinInitialQuantity() != null) {
            predicates.add(cb.greaterThanOrEqualTo(root.get("initialQuantity"), request.getMinInitialQuantity()));
        }
        if (request.getMaxInitialQuantity() != null) {
            predicates.add(cb.lessThanOrEqualTo(root.get("initialQuantity"), request.getMaxInitialQuantity()));
        }

        // remainingQuantity
        if (request.getMinRemainingQuantity() != null) {
            predicates.add(cb.greaterThanOrEqualTo(root.get("remainingQuantity"), request.getMinRemainingQuantity()));
        }
        if (request.getMaxRemainingQuantity() != null) {
            predicates.add(cb.lessThanOrEqualTo(root.get("remainingQuantity"), request.getMaxRemainingQuantity()));
        }

        // expirationDate
        if (request.getExpirationDateFrom() != null) {
            predicates.add(cb.greaterThanOrEqualTo(supplyOrderItemJoin.get("expirationDate"), request.getExpirationDateFrom()));
        }
        if (request.getExpirationDateTo() != null) {
            predicates.add(cb.lessThanOrEqualTo(supplyOrderItemJoin.get("expirationDate"), request.getExpirationDateTo()));
        }

        // receivedDate
        if (request.getReceivedDateFrom() != null) {
            predicates.add(cb.greaterThanOrEqualTo(supplyOrderJoin.get("actualDeliveryDate"), request.getReceivedDateFrom()));
        }
        if (request.getReceivedDateTo() != null) {
            predicates.add(cb.lessThanOrEqualTo(supplyOrderJoin.get("actualDeliveryDate"), request.getReceivedDateTo()));
        }

        // supplierIds
        if (request.getSupplierIds() != null && !request.getSupplierIds().isEmpty()) {
            predicates.add(supplierJoin.get("id").in(request.getSupplierIds()));
        }

        // ingredientIds
        if (request.getIngredientIds() != null && !request.getIngredientIds().isEmpty()) {
            predicates.add(ingredientJoin.get("id").in(request.getIngredientIds()));
        }

        return predicates;
    }

    /**
     * Build Order list based on Sort.
     */
    private List<Order> buildStockBatchOrder(CriteriaBuilder cb,
                                             Sort sort,
                                             Root<StockBatch> root,
                                             Join<StockBatch, Object> supplyOrderItemJoin,
                                             Join<Object, Object> supplyOrderJoin,
                                             Join<Object, Object> supplierJoin,
                                             Join<StockBatch, Object> ingredientJoin) {
        List<Order> orders = new ArrayList<>();
        sort.forEach(order -> {
            String property = order.getProperty();
            Path<?> path = switch (property) {
                case "ingredientName" -> ingredientJoin.get("name");
                case "supplierName" -> supplierJoin.get("name");
                case "receivedDate" -> supplyOrderJoin.get("actualDeliveryDate");
                case "expirationDate" -> supplyOrderItemJoin.get("expirationDate");
                default -> root.get(property);
            };
            orders.add(order.isAscending() ? cb.asc(path) : cb.desc(path));
        });
        return orders;
    }

    /**
     * Create a Count Query to calculate the total number of records that satisfy the filter conditions.
     */
    private Long getCount(CriteriaBuilder cb, StockBatchSearchRequest request) {
        CriteriaQuery<Long> countQuery = cb.createQuery(Long.class);
        Root<StockBatch> countRoot = countQuery.from(StockBatch.class);

        // Perform JOINs in countQuery
        Join<StockBatch, Object> countSupplyOrderItemJoin = countRoot.join("supplyOrderItem", JoinType.INNER);
        Join<Object, Object> countSupplyOrderJoin = countSupplyOrderItemJoin.join("supplyOrder", JoinType.INNER);
        Join<Object, Object> countSupplierJoin = countSupplyOrderJoin.join("supplier", JoinType.INNER);
        Join<StockBatch, Object> countIngredientJoin = countRoot.join("ingredient", JoinType.INNER);

        // Building Predicates for countQuery
        List<Predicate> countPredicates = buildPredicates(cb, countRoot, countSupplyOrderItemJoin, countSupplyOrderJoin, countSupplierJoin, countIngredientJoin, request);

        countQuery.select(cb.countDistinct(countRoot));
        countQuery.where(cb.and(countPredicates.toArray(new Predicate[0])));

        return entityManager.createQuery(countQuery).getSingleResult();
    }

    @Override
    public Page<StockBatchReport> findAllForReport(StockBatchReportSearchRequest request, Pageable pageable) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();

        // Build the query
        CriteriaQuery<StockBatchReport> query = cb.createQuery(StockBatchReport.class);
        Root<StockBatch> root = query.from(StockBatch.class);

        // Perform the necessary JOINs once
        Join<StockBatch, Object> supplyOrderItemJoin = root.join("supplyOrderItem", JoinType.INNER);
        Join<Object, Object> supplyOrderJoin = supplyOrderItemJoin.join("supplyOrder", JoinType.INNER);
        Join<Object, Object> supplierJoin = supplyOrderJoin.join("supplier", JoinType.INNER);
        Join<StockBatch, Object> ingredientJoin = root.join("ingredient", JoinType.INNER);

        // Building Predicates for Filter Conditions
        List<Predicate> predicates = buildReportPredicates(
                cb,
                root,
                supplyOrderItemJoin,
                supplyOrderJoin,
                supplierJoin,
                ingredientJoin,
                request
        );

        // Apply Predicates to Query
        query.where(cb.and(predicates.toArray(new Predicate[0])));

        // Sort handling
        Sort sort = pageable.getSort();
        if (sort.isSorted()) {
            List<Order> orders = buildStockBatchReportOrder(
                    cb,
                    sort,
                    root,
                    supplyOrderItemJoin,
                    supplyOrderJoin,
                    supplierJoin,
                    ingredientJoin
            );
            query.orderBy(orders);
        }

        // Select the required fields for the DTO
        query.select(cb.construct(
                StockBatchReport.class,
                root.get("id"),
                ingredientJoin.get("id"),
                ingredientJoin.get("name"),
                supplyOrderItemJoin.get("quantity").as(Integer.class),
                supplyOrderItemJoin.get("price"),
                supplyOrderItemJoin.get("unitValue"),
                ingredientJoin.get("defaultUnit"),
                supplyOrderItemJoin.get("subtotal"),
                root.get("initialQuantity"),
                root.get("remainingQuantity"),
                supplyOrderJoin.get("id"),
                supplyOrderJoin.get("orderCode"),
                supplyOrderJoin.get("description"),
                supplyOrderJoin.get("actualDeliveryDate"),
                supplyOrderItemJoin.get("expirationDate"),
                supplierJoin.get("id"),
                supplierJoin.get("name")
        ));

        // Create TypedQuery and execute
        TypedQuery<StockBatchReport> typedQuery = entityManager.createQuery(query);
        typedQuery.setFirstResult((int) pageable.getOffset());
        typedQuery.setMaxResults(pageable.getPageSize());

        List<StockBatchReport> resultList = typedQuery.getResultList();

        // Create Count Query to calculate total number of records
        Long total = getReportCount(cb, request);

        return new PageImpl<>(resultList, pageable, total);
    }

    private List<Predicate> buildReportPredicates(CriteriaBuilder cb,
                                                  Root<StockBatch> root,
                                                  Join<StockBatch, Object> supplyOrderItemJoin,
                                                  Join<Object, Object> supplyOrderJoin,
                                                  Join<Object, Object> supplierJoin,
                                                  Join<StockBatch, Object> ingredientJoin,
                                                  StockBatchReportSearchRequest request) {
        StockBatchSearchRequest stockBatchSearchRequest = stockBatchMapper
                .stockBatchReportSearchToStockBatchSearchRequest(request);
        List<Predicate> predicates = buildPredicates(
                cb,
                root,
                supplyOrderItemJoin,
                supplyOrderJoin,
                supplierJoin,
                ingredientJoin,
                stockBatchSearchRequest
        );

        // add more conditions
        // numberOfItems
        if (request.getMinNumberOfItems() != null) {
            predicates.add(cb.greaterThanOrEqualTo(supplyOrderItemJoin.get("quantity"), request.getMinNumberOfItems()));
        }
        if (request.getMaxNumberOfItems() != null) {
            predicates.add(cb.lessThanOrEqualTo(supplyOrderItemJoin.get("quantity"), request.getMaxNumberOfItems()));
        }

        // pricePerItem
        if (request.getMinPricePerItem() != null) {
            predicates.add(cb.greaterThanOrEqualTo(supplyOrderItemJoin.get("price"), request.getMinPricePerItem()));
        }
        if (request.getMaxPricePerItem() != null) {
            predicates.add(cb.lessThanOrEqualTo(supplyOrderItemJoin.get("price"), request.getMaxPricePerItem()));
        }

        // unitValue
        if (request.getMinUnitValue() != null) {
            predicates.add(cb.greaterThanOrEqualTo(supplyOrderItemJoin.get("unitValue"), request.getMinUnitValue()));
        }
        if (request.getMaxUnitValue() != null) {
            predicates.add(cb.lessThanOrEqualTo(supplyOrderItemJoin.get("unitValue"), request.getMaxUnitValue()));
        }

        // subtotal
        if (request.getMinSubtotal() != null) {
            predicates.add(cb.greaterThanOrEqualTo(supplyOrderItemJoin.get("subtotal"), request.getMinSubtotal()));
        }
        if (request.getMaxSubtotal() != null) {
            predicates.add(cb.lessThanOrEqualTo(supplyOrderItemJoin.get("subtotal"), request.getMaxSubtotal()));
        }

        return predicates;
    }

    /**
     * Build Order list based on Sort.
     */
    private List<Order> buildStockBatchReportOrder(CriteriaBuilder cb,
                                   Sort sort,
                                   Root<StockBatch> root,
                                   Join<StockBatch, Object> supplyOrderItemJoin,
                                   Join<Object, Object> supplyOrderJoin,
                                   Join<Object, Object> supplierJoin,
                                   Join<StockBatch, Object> ingredientJoin) {
        List<Order> orders = new ArrayList<>();
        sort.forEach(order -> {
            String property = order.getProperty();
            Path<?> path = switch (property) {
                case "ingredientName" -> ingredientJoin.get("name");
                case "supplierName" -> supplierJoin.get("name");
                case "receivedDate" -> supplyOrderJoin.get("actualDeliveryDate");
                case "expirationDate" -> supplyOrderItemJoin.get("expirationDate");
                case "numberOfItems" -> supplyOrderItemJoin.get("quantity");
                case "pricePerItem" -> supplyOrderItemJoin.get("price");
                case "unitValue" -> supplyOrderItemJoin.get("unitValue");
                case "subtotal" -> supplyOrderItemJoin.get("subtotal");
                default -> root.get(property);
            };
            orders.add(order.isAscending() ? cb.asc(path) : cb.desc(path));
        });
        return orders;
    }

    private Long getReportCount(CriteriaBuilder cb, StockBatchReportSearchRequest request) {
        CriteriaQuery<Long> countQuery = cb.createQuery(Long.class);
        Root<StockBatch> countRoot = countQuery.from(StockBatch.class);

        // Perform JOINs
        Join<StockBatch, Object> supplyOrderItemJoin = countRoot.join("supplyOrderItem", JoinType.INNER);
        Join<Object, Object> supplyOrderJoin = supplyOrderItemJoin.join("supplyOrder", JoinType.INNER);
        Join<Object, Object> supplierJoin = supplyOrderJoin.join("supplier", JoinType.INNER);
        Join<StockBatch, Object> ingredientJoin = countRoot.join("ingredient", JoinType.INNER);

        // Build predicates
        List<Predicate> predicates = buildReportPredicates(
                cb,
                countRoot,
                supplyOrderItemJoin,
                supplyOrderJoin,
                supplierJoin,
                ingredientJoin,
                request
        );

        // Apply predicates
        countQuery.select(cb.countDistinct(countRoot));
        countQuery.where(cb.and(predicates.toArray(new Predicate[0])));

        // Execute count query
        return entityManager.createQuery(countQuery).getSingleResult();
    }
}

