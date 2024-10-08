package com.project.coffeeshopapp.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Where;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "ingredients")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Where(clause = "deleted=false")
public class Ingredient extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 200, nullable = false)
    @NotBlank(message = "Category name cannot be blank")
    @Size(min = 2, max = 500, message = "Category name length must be between {min} and {max} characters")
    private String name;

    @Column(columnDefinition = "TEXT", length = 2000)
    @Size(max = 2000, message = "Description cannot exceed 2000 characters")
    private String description;

    @Column(name = "storage_instructions", columnDefinition = "TEXT", length = 2000)
    @Size(max = 2000, message = "Storage instructions cannot exceed 2000 characters")
    private String storageInstructions;

    @OneToMany(
            mappedBy = "ingredient",
            cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH },
            orphanRemoval = true
    )
    private List<ProductVariantIngredient> productVariants = new ArrayList<>();
}
