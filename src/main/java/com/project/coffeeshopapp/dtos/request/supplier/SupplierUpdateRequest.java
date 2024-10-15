package com.project.coffeeshopapp.dtos.request.supplier;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SupplierUpdateRequest {
    @Size(min = 5, max = 200, message = "Name length must be between {min} and {max} characters")
    private String name;

    @Size(max = 2000, message = "Description cannot exceed 2000 characters")
    private String description;

    @Size(min = 4, max = 2000,  message = "Address length must be between {min} and {max} characters")
    private String address;

    @Size(max = 10, message = "Phone number cannot exceed {max} characters")
    private String phoneNumber;

    @Size(max = 100, message = "Length of email cannot exceed {max} characters")
    private String email;

    @Size(max = 100, message = "Length of otherContactInfo cannot exceed {max} characters")
    private String otherContactInfo;

    @Size(max = 100, message = "Length of taxCode cannot exceed {max} characters")
    private String taxCode;

    @Size(max = 100, message = "Length of bankAccount cannot exceed {max} characters")
    private String bankAccount;

    @Size(max = 100, message = "Length of paymentTerms cannot exceed {max} characters")
    private String paymentTerms;

    @PositiveOrZero(message = "Rating must be non-negative value")
    @DecimalMax(value = "10.00", message = "Rating cannot exceed 10.00")
    private Double rating;
}
