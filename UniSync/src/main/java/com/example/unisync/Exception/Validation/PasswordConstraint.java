package com.example.unisync.Exception.Validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static com.example.unisync.Config.Constants.AT_LEAST_4_CHARACTERS;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Constraint(validatedBy = PasswordValidator.class)
@Target({FIELD})
@Retention(RUNTIME)
public @interface PasswordConstraint {

    String message() default AT_LEAST_4_CHARACTERS;
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
