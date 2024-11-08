package com.project.coffeeshopapp.dtos.projection;

import com.project.coffeeshopapp.enums.OrderStatus;
import com.project.coffeeshopapp.enums.ProductSize;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class OrderItemReport {
    private Long id;
    private Long productVariantId;
    private Long productId;
    private String productName;
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
    private Long orderId;
    private String orderCode;
    private OrderStatus orderStatus;
    private String createdBy;
    private LocalDateTime createdAt;
    private String updatedBy;
    private LocalDateTime updatedAt;
}
