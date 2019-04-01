package com.holzhausen.MedHelper.validators;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class StringToNumberValidator implements ConstraintValidator<StringToNumberConstraint, String> {
    @Override
    public void initialize(StringToNumberConstraint constraintAnnotation) {

    }

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        try {
            Integer.valueOf(s);
        } catch (Exception e){
            return false;
        }
        return true;
    }
}
