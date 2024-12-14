package com.project.coffeeshopapp.validators;

import com.project.coffeeshopapp.customannotations.PasswordMatches;
import com.project.coffeeshopapp.validationservices.contracts.PasswordMatchingCheckable;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PasswordMatchesValidator
        implements ConstraintValidator<PasswordMatches, Object> {

    @Override
    public void initialize(PasswordMatches constraintAnnotation) {
    }
    @Override
    public boolean isValid(Object obj, ConstraintValidatorContext context){
        if (obj instanceof PasswordMatchingCheckable requestDto) {

            customErrorMessage(context);

            String password = requestDto.getPassword();
            String retypePassword = requestDto.getRetypePassword();

            if (password == null || retypePassword == null) {

                return password == retypePassword; // Both must be null to be valid
            }
            return password.equals(retypePassword);
        }
        return false;
    }

    private void customErrorMessage(ConstraintValidatorContext context){
        // disable default error
        context.disableDefaultConstraintViolation();

        // custom error that be thrown
        context.buildConstraintViolationWithTemplate("Passwords mismatch")
                .addPropertyNode("retypePassword")
                .addConstraintViolation();
    }
}