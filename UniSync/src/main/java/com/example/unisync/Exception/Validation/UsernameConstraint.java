package com.example.unisync.Exception.Validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;
import com.example.unisync.Config.Constants;

@Constraint(validatedBy = UsernameValidator.class)
@Target({FIELD})
@Retention(RUNTIME)
public @interface UsernameConstraint {

    String message() default Constants.ONLY_LETTERS_OR_DIGITS;
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
