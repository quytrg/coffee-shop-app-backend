package com.project.coffeeshopapp.dtos.request.supplyorder;

import com.project.coffeeshopapp.dtos.request.base.BasePaginationSortRequest;
import com.project.coffeeshopapp.enums.*;
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
public class SupplyOrderSearchRequest extends BasePaginationSortRequest<SupplyOrderSortField> {
    // Additional search-specific fields
    private String keyword;
    private BigDecimal minTotalAmount;
    private BigDecimal maxTotalAmount;
    private SupplyOrderStatus status;
    private PaymentMethod paymentMethod;
    private PaymentStatus paymentStatus;
    private LocalDateTime expectedDeliveryDateFrom;
    private LocalDateTime expectedDeliveryDateTo;
    private LocalDateTime actualDeliveryDateFrom;
    private LocalDateTime actualDeliveryDateTo;
    private List<Long> supplierIds = new ArrayList<>();
    private List<Long> ingredientIds = new ArrayList<>();

    @Override
    protected List<SupplyOrderSortField> initSortBy() {
        return Collections.singletonList(SupplyOrderSortField.UPDATED_AT);
    }

    @Override
    protected List<SortDirection> initSortDir() {
        return Collections.singletonList(SortDirection.DESC);
    }
}
