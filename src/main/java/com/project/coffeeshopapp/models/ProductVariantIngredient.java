package com.project.coffeeshopapp.models;

import com.project.coffeeshopapp.enums.MeasurementUnit;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.SQLRestriction;
import org.hibernate.annotations.Where;

import java.math.BigDecimal;

@Entity
//@Table(
//        name = "productvariant_ingredients",
//        uniqueConstraints = {
//        @UniqueConstraint(
//                columnNames = {"productvariant_id", "ingredient_id"},
//                name = "uk_productvariant_ingredient"
//        )
//})
@Table(name = "productvariant_ingredients")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SQLRestriction("deleted=false")
public class ProductVariantIngredient extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "productvariant_id", nullable = false)
    private ProductVariant productVariant;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ingredient_id", nullable = false)
    private Ingredient ingredient;

    @Column(name = "quantity", precision = 10, scale = 2, nullable = false)
    @NotNull(message = "Quantity is mandatory")
    @Positive(message = "Quantity must be positive")
    private BigDecimal quantity;

    @Column(name = "unit", nullable = false)
    @Enumerated(EnumType.STRING)
    private MeasurementUnit unit;

    @Column(name = "preparation_order", nullable = false)
    private Integer preparationOrder;

    @Column(name = "description", columnDefinition = "TEXT", length = 2000)
    @Size(max = 2000, message = "Description cannot exceed 2000 characters")
    private String description;
}
