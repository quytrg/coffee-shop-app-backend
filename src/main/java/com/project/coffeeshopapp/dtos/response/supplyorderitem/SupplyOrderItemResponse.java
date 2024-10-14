package com.project.coffeeshopapp.dtos.response.supplyorderitem;

import com.project.coffeeshopapp.enums.SupplyUnit;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SupplyOrderItemResponse {
    private Long id;
    private Long ingredientId;
    private String ingredientName;
    private BigDecimal price;
    private Integer quantity;
    private Integer discount;
    private BigDecimal subtotal;
    private LocalDateTime expirationDate;
    private SupplyUnit unit;
    private BigDecimal unitValue;
    private SupplyUnit baseUnit;
    private String description;
}
