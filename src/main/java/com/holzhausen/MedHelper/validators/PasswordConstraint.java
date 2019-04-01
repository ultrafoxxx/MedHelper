package com.holzhausen.MedHelper.validators;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = PasswordValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface PasswordConstraint {

    String message() default "Ten ciąg musi zawierać małe litery, duże litery i cyfry";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

}
