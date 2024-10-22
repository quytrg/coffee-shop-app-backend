package com.project.coffeeshopapp.dtos.response.stockbatch;

import com.project.coffeeshopapp.enums.MeasurementUnit;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StockBatchSummaryResponse {
    private Long id;
    private Long ingredientId;
    private String ingredientName;
    private MeasurementUnit defaultUnit;
    private BigDecimal initialQuantity;
    private BigDecimal remainingQuantity;
    private Long supplyOrderId;
    private String supplyOrderCode;
    private LocalDateTime receivedDate;
    private LocalDateTime expirationDate;
    private Long supplierId;
    private String supplierName;
}
