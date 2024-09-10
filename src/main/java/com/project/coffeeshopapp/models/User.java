package com.project.coffeeshopapp.models;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Where;

import java.util.Date;

@Entity
@Table(name = "users")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Where(clause = "deleted=false")
@Builder
public class User extends BaseEntity {
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

    @Column(name = "is_active")
    private Boolean isActive;

    @Column(name = "sex")
    private Boolean sex;

    @Column(name = "date_of_birth")
    @Temporal(TemporalType.DATE)
    private Date dateOfBirth;
}