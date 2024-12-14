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
public class StockBatchReportResponse {
    private Long id;
    private Long ingredientId;
    private String ingredientName;
    private Integer numberOfItems;
    private BigDecimal pricePerItem;
    /**
     * The amount of the ingredient per item, expressed in the ingredient's default unit.
     * For example, if ordering sugar in 2 kg bags and the default unit is grams,
     * unitValue should be 2000 (since 1 kg = 1000 grams).
     */
    private BigDecimal unitValue;
    private MeasurementUnit defaultUnit;
    private BigDecimal subtotal;
    private BigDecimal initialQuantity;
    private BigDecimal remainingQuantity;
    private Long supplyOrderId;
    private String supplyOrderCode;
    private String supplyOrderDescription;
    private LocalDateTime receivedDate;
    private LocalDateTime expirationDate;
    private Long supplierId;
    private String supplierName;
}
