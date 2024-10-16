package com.project.coffeeshopapp.dtos.request.supplyorderitem;

import com.project.coffeeshopapp.enums.MeasurementUnit;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SupplyOrderItemRequest {
    @NotNull(message = "Ingredient ID is mandatory")
    private Long ingredientId;

    @Size(max = 2000, message = "Description cannot exceed 2000 characters")
    private String description;

    @NotNull(message = "Price is mandatory")
    @PositiveOrZero(message = "Price must be non-negative value")
    @DecimalMax(value = "999999999.99", message = "Price cannot exceed 999,999,999.99")
    private BigDecimal price;

    @NotNull(message = "Quantity is mandatory")
    @PositiveOrZero(message = "Quantity must be non-negative value")
    private Integer quantity;

    @PositiveOrZero(message = "Discount must be non-negative value")
    @Max(value = 100, message = "Discount cannot exceed 100%")
    private Integer discount;

    private LocalDateTime expirationDate;

    /**
     * The unit of measurement for the item, which must match the ingredient's default unit.
     */
    @NotNull(message = "Unit is mandatory")
    private MeasurementUnit unit;
    
    /**
     * The amount of the ingredient per item, expressed in the ingredient's default unit.
     * For example, if ordering sugar in 2 kg bags and the default unit is grams,
     * unitValue should be 2000 (since 1 kg = 1000 grams).
     */
    @NotNull(message = "Unit value is mandatory")
    @PositiveOrZero(message = "Unit value must be non-negative value")
    private BigDecimal unitValue;
}
