package com.project.coffeeshopapp.dtos.request.orderitem;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderItemCreateRequest {
    @NotNull(message = "productVariantId is mandatory")
    private Long productVariantId;

    @Size(max = 2000, message = "Description cannot exceed 2000 characters")
    private String description;

    /**
     * The number of items.
     */
    @NotNull(message = "Quantity is mandatory")
    @PositiveOrZero(message = "Quantity must be non-negative value")
    private Integer quantity;

    @PositiveOrZero(message = "Discount must be non-negative value")
    @Max(value = 100, message = "Discount cannot exceed 100%")
    private Integer discount;
}
