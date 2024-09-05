package com.project.coffeeshopapp.models;
import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Entity(name = "permissions")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Permission {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "name")
    private String name;

    @ManyToMany(mappedBy = "permissions")
    private Set<Role> roles;
}