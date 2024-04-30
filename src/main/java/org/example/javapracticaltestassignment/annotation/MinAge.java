package org.example.javapracticaltestassignment.annotation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import org.example.javapracticaltestassignment.validator.MinAgeValidator;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = MinAgeValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface MinAge {
    String message() default "Age does not meet the minimum";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}