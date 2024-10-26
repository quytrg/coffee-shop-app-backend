package com.project.coffeeshopapp.dtos.request.stockbatch;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StockBatchUpdateRequest {
    private BigDecimal remainingQuantity;
    private LocalDateTime expirationDate;
}
