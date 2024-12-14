package com.project.coffeeshopapp.dtos.request.orderitem;

import com.project.coffeeshopapp.dtos.request.base.BasePaginationSortRequest;
import com.project.coffeeshopapp.enums.OrderItemReportSortField;
import com.project.coffeeshopapp.enums.OrderStatus;
import com.project.coffeeshopapp.enums.ProductSize;
import com.project.coffeeshopapp.enums.SortDirection;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString(callSuper = true)
public class OrderItemReportSearchRequest extends BasePaginationSortRequest<OrderItemReportSortField> {
    // Additional search-specific fields
    private String keyword; // productName, orderItemDescription, orderCode
    private List<Long> productVariantIds = new ArrayList<>();
    private List<Long> productIds = new ArrayList<>();
    private ProductSize productVariantSize;
    private BigDecimal minPrice;
    private BigDecimal maxPrice;
    private Integer minQuantity;
    private Integer maxQuantity;
    private Integer minDiscount;
    private Integer maxDiscount;
    private BigDecimal minSubtotal;
    private BigDecimal maxSubtotal;
    private List<Long> orderIds = new ArrayList<>();
    private List<String> orderCodes = new ArrayList<>();
    private OrderStatus orderStatus;
    private LocalDateTime createdAtFrom;
    private LocalDateTime createdAtTo;
    private LocalDateTime updatedAtFrom;
    private LocalDateTime updatedAtTo;
    private List<Long> createdByIds = new ArrayList<>();
    private List<Long> updatedByIds = new ArrayList<>();

    @Override
    protected List<OrderItemReportSortField> initSortBy() {
        return List.of(OrderItemReportSortField.UPDATED_AT);
    }

    @Override
    protected List<SortDirection> initSortDir() {
        return List.of(SortDirection.DESC);
    }
}
