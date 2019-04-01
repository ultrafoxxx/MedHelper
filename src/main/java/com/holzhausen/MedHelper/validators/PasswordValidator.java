package com.holzhausen.MedHelper.validators;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PasswordValidator implements ConstraintValidator<PasswordConstraint, String> {
    @Override
    public void initialize(PasswordConstraint constraintAnnotation) {

    }

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        Pattern pattern = Pattern.compile(
                "(?=.*[A-Z])" +
                        "(?=.*[a-z])" +
                        "(?=.*\\d)" +
                        "^[^\\d]" +
                        ".*" +
                        "[a-zA-Z\\d]$");
        Matcher matcher = pattern.matcher(s);
        return matcher.matches();
    }
}
