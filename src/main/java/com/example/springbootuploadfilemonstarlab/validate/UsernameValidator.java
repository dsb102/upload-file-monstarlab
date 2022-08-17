package com.example.springbootuploadfilemonstarlab.validate;

import com.example.springbootuploadfilemonstarlab.constraints.Username;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class UsernameValidator implements ConstraintValidator<Username, String> {
    private static final int minLength = 3;
    private static final int maxLength = 30;

    @Override
    public void initialize(Username constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String usernameStr, ConstraintValidatorContext constraintValidatorContext) {
        int lengthUsername = usernameStr.length();
        return usernameStr != null && !usernameStr.isEmpty() && lengthUsername >= 3 && lengthUsername <= 30;
    }
}
