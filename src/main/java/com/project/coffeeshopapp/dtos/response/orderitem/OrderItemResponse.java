package com.project.coffeeshopapp.dtos.response.orderitem;

import com.project.coffeeshopapp.enums.ProductSize;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderItemResponse {
    private Long id;
    private Long productVariantId;
    private String productVariantName;
    private ProductSize productVariantSize;
    private String description;
    /**
     * Price per item
     */
    private BigDecimal price;
    /**
     * The number of items.
     */
    private Integer quantity;
    private Integer discount;
    private BigDecimal subtotal;
}
