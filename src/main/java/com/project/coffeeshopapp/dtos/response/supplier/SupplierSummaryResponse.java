package com.project.coffeeshopapp.dtos.response.supplier;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SupplierSummaryResponse {
    private Long id;
    private String name;
    private String address;
    private String phoneNumber;
    private String email;
    private Double rating;
}
