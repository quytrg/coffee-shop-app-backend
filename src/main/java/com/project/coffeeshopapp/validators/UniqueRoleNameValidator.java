package com.project.coffeeshopapp.validators;

import com.project.coffeeshopapp.customannotations.UniqueRoleName;
import com.project.coffeeshopapp.repositories.RoleRepository;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UniqueRoleNameValidator implements ConstraintValidator<UniqueRoleName, String> {

    private final RoleRepository roleRepository;

    @Override
    public void initialize(UniqueRoleName constraintAnnotation) {
    }

    @Override
    public boolean isValid(String roleName, ConstraintValidatorContext context) {
        if (roleName == null) {
            return true;
        }
        return !roleRepository.existsByName(roleName);
    }
}