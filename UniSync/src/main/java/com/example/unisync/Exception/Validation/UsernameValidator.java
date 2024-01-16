package com.example.unisync.Exception.Validation;

import com.example.unisync.Config.Constants;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class UsernameValidator implements ConstraintValidator<UsernameConstraint, String> {
    @Override
    public boolean isValid(String username, ConstraintValidatorContext context) {
        return username != null && username.matches(Constants.REGEX_USERNAME);
    }
}
