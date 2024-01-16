package com.example.unisync.Exception.Validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static com.example.unisync.Config.Constants.INVALID_EMAIL_FORMAT;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Constraint(validatedBy = EmailValidator.class)
@Target({FIELD})
@Retention(RUNTIME)
public @interface EmailConstraint {

    String message() default INVALID_EMAIL_FORMAT;
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
