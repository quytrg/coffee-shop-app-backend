package com.project.coffeeshopapp.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Where;


import java.math.BigDecimal;

@Entity
@Table(name = "order_items")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Where(clause = "deleted=false")
public class OrderItem extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "description", columnDefinition = "TEXT", length = 2000)
    @Size(max = 2000, message = "Description cannot exceed 2000 characters")
    private String description;

    /**
     * Price per item
     */
    @Column(name = "price", precision = 11, scale = 2, nullable = false)
    @NotNull(message = "Price is mandatory")
    @PositiveOrZero(message = "Price must be non-negative value")
    @DecimalMax(value = "999999999.99", message = "Price cannot exceed 999,999,999.99")
    private BigDecimal price;

    /**
     * The number of items.
     */
    @Column(name = "quantity", nullable = false)
    @NotNull(message = "Quantity is mandatory")
    @PositiveOrZero(message = "Quantity must be non-negative value")
    private Integer quantity;

    @Column(name = "discount", nullable = false)
    @PositiveOrZero(message = "Discount must be non-negative value")
    @Max(value = 100, message = "Discount cannot exceed 100%")
    private Integer discount;

    @Column(name = "subtotal", precision = 11, scale = 2, nullable = false)
    @NotNull(message = "Subtotal is mandatory")
    @PositiveOrZero(message = "Subtotal must be non-negative value")
    @DecimalMax(value = "999999999.99", message = "Subtotal cannot exceed 999,999,999.99")
    private BigDecimal subtotal;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "productvariant_id", nullable = false)
    private ProductVariant productVariant;
}
