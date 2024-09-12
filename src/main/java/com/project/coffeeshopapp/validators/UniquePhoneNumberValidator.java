package com.project.coffeeshopapp.validators;

import com.project.coffeeshopapp.customannotations.UniquePhoneNumber;
import com.project.coffeeshopapp.repositories.UserRepository;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UniquePhoneNumberValidator implements ConstraintValidator<UniquePhoneNumber, String> {

    private final UserRepository userRepository;

    @Override
    public void initialize(UniquePhoneNumber constraintAnnotation) {
    }

    @Override
    public boolean isValid(String phoneNumber, ConstraintValidatorContext context) {
        if (phoneNumber == null) {
            return true;
        }
        return !userRepository.existsByPhoneNumber(phoneNumber);
    }
}

