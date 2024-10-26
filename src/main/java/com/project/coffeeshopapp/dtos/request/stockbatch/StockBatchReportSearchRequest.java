package com.project.coffeeshopapp.dtos.request.stockbatch;

import com.project.coffeeshopapp.dtos.request.base.BasePaginationSortRequest;
import com.project.coffeeshopapp.enums.MeasurementUnit;
import com.project.coffeeshopapp.enums.SortDirection;
import com.project.coffeeshopapp.enums.StockBatchReportSortField;
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
public class StockBatchReportSearchRequest extends BasePaginationSortRequest<StockBatchReportSortField> {
    // Additional search-specific fields
    private String keyword;
    private MeasurementUnit defaultUnit;
    private BigDecimal minInitialQuantity;
    private BigDecimal maxInitialQuantity;
    private BigDecimal minRemainingQuantity;
    private BigDecimal maxRemainingQuantity;
    private LocalDateTime expirationDateFrom;
    private LocalDateTime expirationDateTo;
    private LocalDateTime receivedDateFrom;
    private LocalDateTime receivedDateTo;
    private List<Long> ingredientIds = new ArrayList<>();
    private List<Long> supplierIds = new ArrayList<>();
    private Integer minNumberOfItems;
    private Integer maxNumberOfItems;
    private BigDecimal minPricePerItem;
    private BigDecimal maxPricePerItem;
    private BigDecimal minUnitValue;
    private BigDecimal maxUnitValue;
    private BigDecimal minSubtotal;
    private BigDecimal maxSubtotal;

    @Override
    protected List<StockBatchReportSortField> initSortBy() {
        return List.of(
                StockBatchReportSortField.EXPIRATION_DATE,
                StockBatchReportSortField.REMAINING_QUANTITY,
                StockBatchReportSortField.RECEIVED_DATE
        );
    }

    @Override
    protected List<SortDirection> initSortDir() {
        return List.of(
                SortDirection.ASC,
                SortDirection.ASC,
                SortDirection.DESC
        );
    }
}
