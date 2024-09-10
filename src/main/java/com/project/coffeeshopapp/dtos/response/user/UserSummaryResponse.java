package com.project.coffeeshopapp.dtos.response.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserSummaryResponse {
    private Long id;
    private String fullName;
    private String phoneNumber;
    private String email;
    private Boolean isActive;
}