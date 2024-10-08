package com.project.coffeeshopapp.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Where;

@Entity
@Table(name = "suppliers")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Where(clause = "deleted=false")
public class Supplier extends BaseEntity {
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

    @Column(name = "address", columnDefinition = "TEXT", length = 2000, nullable = false)
    @Size(max = 2000, message = "Address cannot exceed 2000 characters")
    private String address;

    @Column(name = "phone_number", length = 10, nullable = false)
    @NotBlank(message = "Phone number cannot be blank")
    @Size(max = 10, message = "Phone number cannot exceed 10 characters")
    private String phoneNumber;

    @Column(name = "email", length = 100)
    @Size(max = 100, message = "Length of email cannot exceed 100 characters")
    private String email;

    @Column(name = "other_contact_info", length = 5000)
    @Size(max = 100, message = "Length of otherContactInfo cannot exceed 100 characters")
    private String otherContactInfo;

    @Column(name = "tax_code", length = 100)
    @Size(max = 100, message = "Length of taxCode cannot exceed 100 characters")
    private String taxCode;

    @Column(name = "bank_account", length = 100)
    @Size(max = 100, message = "Length of bankAccount cannot exceed 100 characters")
    private String bankAccount;

    @Column(name = "payment_terms", length = 100)
    @Size(max = 100, message = "Length of paymentTerms cannot exceed 100 characters")
    private String paymentTerms;

    @Column(name = "rating")
    @PositiveOrZero(message = "Rating must be non-negative value")
    @DecimalMax(value = "10.00", message = "Rating cannot exceed 10.00")
    private Double rating;
}
