package com.project.coffeeshopapp.dtos.request.stockbatch;

import com.project.coffeeshopapp.dtos.request.base.BasePaginationSortRequest;
import com.project.coffeeshopapp.enums.MeasurementUnit;
import com.project.coffeeshopapp.enums.SortDirection;
import com.project.coffeeshopapp.enums.StockBatchSortField;
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
public class StockBatchSearchRequest extends BasePaginationSortRequest<StockBatchSortField>{

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

    @Override
    protected List<StockBatchSortField> initSortBy() {
        return List.of(
                StockBatchSortField.EXPIRATION_DATE,
                StockBatchSortField.REMAINING_QUANTITY,
                StockBatchSortField.RECEIVED_DATE
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
