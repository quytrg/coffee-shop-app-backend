package com.project.coffeeshopapp.models;

import com.project.coffeeshopapp.enums.ImageAssociationType;
import com.project.coffeeshopapp.enums.ProductStatus;
import com.project.coffeeshopapp.models.contracts.ImageAssociable;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.Where;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "products")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Where(clause = "deleted=false")
@Builder
public class Product extends BaseEntity implements ImageAssociable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", length = 200, nullable = false)
    @NotBlank(message = "Product name cannot be blank")
    @Size(min = 5, max = 200, message = "Name length must be between {min} and {max} characters")
    private String name;

    @Column(name = "description", columnDefinition = "TEXT", length = 2000)
    @Size(max = 2000, message = "Description cannot exceed 2000 characters")
    private String description;

    @Column(name = "position", nullable = false)
    private Long position;

    @OneToMany(
            mappedBy = "product",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    @Where(clause = "image_association_type = 'PRODUCT'")
    @BatchSize(size = 20)
    private List<Image> images = new ArrayList<>();

    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    private ProductStatus status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", nullable=false)
    private Category category;

    @Override
    public void addImage(Image image) {
        if (images == null) {
            images = new ArrayList<Image>();
        }
        images.add(image);
        image.setProduct(this);
        image.setImageAssociationType(ImageAssociationType.PRODUCT);
    }

    @Override
    public void removeImage(Image image) {
        images.remove(image);
        image.setProduct(null);
        image.setImageAssociationType(null);
    }

    @OneToMany(
            mappedBy = "product",
            cascade = { CascadeType.PERSIST, CascadeType.MERGE }
    )
    @BatchSize(size = 5)
    private List<ProductVariant> productVariants = new ArrayList<>();
}
