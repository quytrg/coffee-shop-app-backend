package com.project.coffeeshopapp.customannotations;

import com.project.coffeeshopapp.validators.UniquePhoneNumberValidator;
import jakarta.validation.Constraint;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import jakarta.validation.Payload;

@Target({ FIELD, PARAMETER })
@Retention(RUNTIME)
@Constraint(validatedBy = UniquePhoneNumberValidator.class)
@Documented
public @interface UniquePhoneNumber {
    String message() default "Phone number already exists";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}