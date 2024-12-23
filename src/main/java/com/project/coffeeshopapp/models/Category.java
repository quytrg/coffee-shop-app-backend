package com.project.coffeeshopapp.models;

import com.project.coffeeshopapp.enums.CategoryStatus;
import com.project.coffeeshopapp.enums.ImageAssociationType;
import com.project.coffeeshopapp.models.contracts.ImageAssociable;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.Where;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "categories")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Where(clause = "deleted=false")
@Builder
public class Category extends BaseEntity implements ImageAssociable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", length = 200, nullable = false)
    @NotBlank(message = "Category name cannot be blank")
    @Size(min = 5, max = 200, message = "Category name length must be between {min} and {max} characters")
    private String name;

    @Column(name = "description", columnDefinition = "TEXT", length = 2000)
    @Size(max = 2000, message = "Description cannot exceed 2000 characters")
    private String description;

    @OneToMany(
            mappedBy = "category",
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    @Where(clause = "image_association_type = 'CATEGORY'")
    @BatchSize(size = 20)
    private List<Image> images = new ArrayList<>();

    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    private CategoryStatus status;

    @OneToMany(mappedBy = "category",
            fetch = FetchType.LAZY,
            cascade = { CascadeType.PERSIST, CascadeType.MERGE }
    )
    private Set<Product> products = new HashSet<>();

    @Override
    public void addImage(Image image) {
        if (images == null) {
            images = new ArrayList<Image>();
        }
        images.add(image);
        image.setCategory(this);
        image.setImageAssociationType(ImageAssociationType.CATEGORY);
    }

    @Override
    public void removeImage(Image image) {
        images.remove(image);
        image.setCategory(null);
        image.setImageAssociationType(null);
    }
}
