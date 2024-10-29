package com.project.coffeeshopapp.models;

import com.project.coffeeshopapp.enums.ProductSize;
import com.project.coffeeshopapp.enums.ProductVariantStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.Where;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "productvariants")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Where(clause = "deleted=false")
public class ProductVariant extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "size", nullable = false)
    @Enumerated(EnumType.STRING)
    private ProductSize size;

    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    private ProductVariantStatus status;

    @Column(name = "price", precision = 8, scale = 2, nullable = false)
    @NotNull(message = "Price is mandatory")
    @PositiveOrZero(message = "Price must be non-negative value")
    @DecimalMax(value = "999999.99", message = "Price cannot exceed 999,999.99")
    private BigDecimal price;

    @Column(name = "cost", precision = 8, scale = 2, nullable = false)
    @NotNull(message = "Cost is mandatory")
    @PositiveOrZero(message = "Cost must be non-negative value")
    @DecimalMax(value = "999999.99", message = "Cost cannot exceed 999,999.99")
    private BigDecimal cost;

    @Column(name = "description", columnDefinition = "TEXT", length = 2000)
    @Size(max = 2000, message = "Description cannot exceed 2000 characters")
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable=false)
    private Product product;

    @OneToMany(
            mappedBy = "productVariant",
            cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH },
            orphanRemoval = true
    )
    @OrderBy("preparationOrder ASC")
    @BatchSize(size = 20)
    private List<ProductVariantIngredient> ingredients = new ArrayList<>();

    public void setIngredients(List<ProductVariantIngredient> ingredients) {
        this.ingredients.clear();
        if (ingredients != null) {
            ingredients.forEach(ingredient -> ingredient.setProductVariant(this));
            this.ingredients.addAll(ingredients);
        }
    }

    @OneToMany(
            mappedBy = "productVariant",
            cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH }
    )
    @BatchSize(size = 20)
    private List<OrderItem> orderItems = new ArrayList<>();
}
