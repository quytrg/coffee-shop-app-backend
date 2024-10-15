package com.project.coffeeshopapp.models;

import com.project.coffeeshopapp.enums.ImageAssociationType;
import com.project.coffeeshopapp.enums.UserStatus;
import com.project.coffeeshopapp.models.contracts.ImageAssociable;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.Where;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "users")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Where(clause = "deleted=false")
@Builder
public class User extends BaseEntity implements ImageAssociable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "full_name", length = 200)
    private String fullName;

    @Column(name = "phone_number", length = 10, nullable = false, unique = true)
    private String phoneNumber;

    @Column(name = "email", length = 100, nullable = false, unique = true)
    private String email;

    @Column(name = "address", length = 200)
    private String address;

    @Column(name = "password", length = 100, nullable = false)
    private String password;

    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    private UserStatus status;

    @Column(name = "sex")
    private Boolean sex;

    @Column(name = "date_of_birth")
    private LocalDateTime dateOfBirth;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "role_id", nullable=false)
    private Role role;

    @OneToMany(
            mappedBy = "user",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    @Where(clause = "image_association_type = 'USER'")
    @BatchSize(size = 20)
    private List<Image> images = new ArrayList<>();

    @Override
    public void addImage(Image image) {
        if (images == null) {
            images = new ArrayList<Image>();
        }
        images.add(image);
        image.setUser(this);
        image.setImageAssociationType(ImageAssociationType.USER);
    }

    @Override
    public void removeImage(Image image) {
        images.remove(image);
        image.setUser(null);
        image.setImageAssociationType(null);
    }
}