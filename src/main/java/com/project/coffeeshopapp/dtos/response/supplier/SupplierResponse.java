package com.project.coffeeshopapp.dtos.response.supplier;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SupplierResponse {
    private Long id;
    private String name;
    private String description;
    private String address;
    private String phoneNumber;
    private String email;
    private String otherContactInfo;
    private String taxCode;
    private String bankAccount;
    private String paymentTerms;
    private Double rating;
}
