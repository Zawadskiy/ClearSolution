package org.example.javapracticaltestassignment.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.example.javapracticaltestassignment.annotation.MinAge;
import org.springframework.beans.factory.annotation.Value;

import java.time.LocalDate;


public class MinAgeValidator implements ConstraintValidator<MinAge, LocalDate> {

    @Value("${age}")
    private int age;

    @Override
    public boolean isValid(LocalDate value, ConstraintValidatorContext context) {
        if (value != null) {
            return value.plusYears(age).isBefore(LocalDate.now());
        }
        return true;
    }

    @Override
    public void initialize(MinAge constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }
}
