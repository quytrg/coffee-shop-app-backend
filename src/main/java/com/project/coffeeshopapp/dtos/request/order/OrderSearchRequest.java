package com.project.coffeeshopapp.dtos.request.order;

import com.project.coffeeshopapp.dtos.request.base.BasePaginationSortRequest;
import com.project.coffeeshopapp.enums.OrderSortField;
import com.project.coffeeshopapp.enums.OrderStatus;
import com.project.coffeeshopapp.enums.PaymentMethod;
import com.project.coffeeshopapp.enums.SortDirection;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Getter
@Setter
@ToString(callSuper = true)
public class OrderSearchRequest extends BasePaginationSortRequest<OrderSortField> {
    // Additional search-specific fields
    private String keyword;
    private BigDecimal minTotalAmount;
    private BigDecimal maxTotalAmount;
    private OrderStatus status;
    private PaymentMethod paymentMethod;
    private BigDecimal minReceivedAmount;
    private BigDecimal maxReceivedAmount;
    private BigDecimal minReturnAmount;
    private BigDecimal maxReturnAmount;
    private List<Long> userIds = new ArrayList<>();
    private LocalDateTime createdAtFrom;
    private LocalDateTime createdAtTo;
    private LocalDateTime updatedAtFrom;
    private LocalDateTime updatedAtTo;

    @Override
    protected List<OrderSortField> initSortBy() {
        return Collections.singletonList(OrderSortField.UPDATED_AT);
    }

    @Override
    protected List<SortDirection> initSortDir() {
        return Collections.singletonList(SortDirection.DESC);
    }
}
