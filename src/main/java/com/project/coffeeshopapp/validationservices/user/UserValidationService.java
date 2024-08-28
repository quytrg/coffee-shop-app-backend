package com.project.coffeeshopapp.validationservices.user;

import com.project.coffeeshopapp.customexceptions.PasswordMismatchException;
import com.project.coffeeshopapp.dtos.UserDTO;
import com.project.coffeeshopapp.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserValidationService implements IUserValidationService {
    private final UserRepository userRepository;

    @Override
    public void validateUserForCreation(UserDTO userDTO) {
        // check if password matches retype-password
        if (!userDTO.getPassword().equals(userDTO.getRetypePassword())) {
            throw new PasswordMismatchException();
        }

        // check if phoneNumber already exists
        if(userRepository.existsByPhoneNumber(userDTO.getPhoneNumber())) {
            throw new DataIntegrityViolationException("Phone number already exists");
        }
    }
}
