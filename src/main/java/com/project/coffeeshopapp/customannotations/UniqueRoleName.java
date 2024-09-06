package com.project.coffeeshopapp.customannotations;

import com.project.coffeeshopapp.validators.UniqueRoleNameValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({ FIELD, PARAMETER })
@Retention(RUNTIME)
@Constraint(validatedBy = UniqueRoleNameValidator.class)
@Documented
public @interface UniqueRoleName {
    String message() default "Role name already exists";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
