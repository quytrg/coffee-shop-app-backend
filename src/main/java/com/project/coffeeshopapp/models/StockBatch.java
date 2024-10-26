package com.project.coffeeshopapp.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Where;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "stock_batches")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Where(clause = "deleted=false")
public class StockBatch extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "initial_quantity", nullable = false)
    @NotNull(message = "Initial quantity is mandatory")
    @PositiveOrZero(message = "Initial quantity must be non-negative value")
    private BigDecimal initialQuantity;

    @Column(name = "remaining_quantity", nullable = false)
    @NotNull(message = "Remaining quantity is mandatory")
    @PositiveOrZero(message = "Remaining quantity must be non-negative value")
    private BigDecimal remainingQuantity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ingredient_id", nullable = false)
    private Ingredient ingredient;

    @OneToOne(mappedBy = "stockBatch", fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    private SupplyOrderItem supplyOrderItem;
}