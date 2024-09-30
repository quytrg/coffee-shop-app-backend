package com.project.coffeeshopapp.models;

import com.project.coffeeshopapp.enums.RoleStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.Where;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "roles")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Where(clause = "deleted=false")
public class Role extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "role",
            fetch = FetchType.LAZY,
            cascade = { CascadeType.PERSIST, CascadeType.MERGE }
    )
    private Set<User> users = new HashSet<>();

    // fetch permissions along with role for authentication
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name="role_permissions",
            joinColumns = @JoinColumn(name = "role_id"),
            inverseJoinColumns = @JoinColumn(name = "permission_id")
    )
    @OrderBy("name ASC")
    @BatchSize(size = 20)
    private Set<Permission> permissions = new HashSet<>();

    @Column(name = "name", nullable = false, unique = true)
    @NotBlank(message = "Role name cannot be blank")
    @Size(max = 100, message = "Role name cannot exceed 100 characters")
    private String name;

    private String description;

    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    private RoleStatus status;
}
