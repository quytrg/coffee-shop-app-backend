package com.project.coffeeshopapp.converters;

import com.project.coffeeshopapp.dtos.UserDTO;
import com.project.coffeeshopapp.models.User;
import org.springframework.stereotype.Component;


@Component
public class UserConverter {
    public User convertToUser(UserDTO userDTO) {
        return User.builder()
                .fullName(userDTO.getFullName())
                .phoneNumber(userDTO.getPhoneNumber())
                .email(userDTO.getEmail())
                .password(userDTO.getPassword())
                .address(userDTO.getAddress())
                .sex(userDTO.getSex())
                .dateOfBirth(userDTO.getDateOfBirth())
                .isActive(true)
                .build();
    }
}
