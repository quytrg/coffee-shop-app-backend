package com.project.coffeeshopapp.models;

import com.project.coffeeshopapp.enums.ImageAssociationType;
import lombok.*;
import org.hibernate.annotations.Where;
import jakarta.persistence.*;

@Entity
@Table(name = "images")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Where(clause = "deleted=false")
public class Image extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String url;
    private String publicId; // Identifier from Cloudinary or your storage service
    private String altText;
    private Integer displayOrder;

    @Enumerated(EnumType.STRING)
    @Column(name = "image_association_type")
    private ImageAssociationType imageAssociationType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    // Validation to ensure an image is associated with only one entity
    @PrePersist
    @PreUpdate
    private void validateAssociation() {
        int associations = 0;
        if (product != null) associations++;
        if (category != null) associations++;
        if (user != null) associations++;
        if (associations > 1) {
            throw new IllegalStateException("An image must be associated with exactly one entity (Product, Category, or User).");
        }
    }

}