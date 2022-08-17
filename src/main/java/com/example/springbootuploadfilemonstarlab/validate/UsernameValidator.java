package com.example.springbootuploadfilemonstarlab.validate;

import com.example.springbootuploadfilemonstarlab.constraints.Username;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class UsernameValidator implements ConstraintValidator<Username, String> {
    private static final int MIN_LENGTH = 3;
    private static final int MAX_LENGTH = 30;

    @Override
    public void initialize(Username constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String usernameStr, ConstraintValidatorContext constraintValidatorContext) {
        int lengthUsername = usernameStr.length();
        return usernameStr != null && !usernameStr.isEmpty() && lengthUsername >= MIN_LENGTH && lengthUsername <= MAX_LENGTH;
    }
}
