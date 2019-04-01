package com.holzhausen.MedHelper.validators;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = StringToNumberValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface StringToNumberConstraint {
    String message() default "Ten ciąg musi być liczbą";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
