package org.example.javapracticaltestassignment.validator;

import org.example.javapracticaltestassignment.dto.request.UserRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.time.LocalDate;

@Component
public class UserRequestValidator implements Validator {
    
    @Value("${age}")
    private long years;
    
    @Override
    public boolean supports(Class<?> clazz) {
        return UserRequest.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {

        if (errors.hasErrors()) {
            return;
        }

        if (target instanceof UserRequest request) {
            if (request.getBirthDate().plusYears(years).isAfter(LocalDate.now())) {
                errors.rejectValue("birthDate", "%s %d %s".formatted("User must be at least", years, "years old"));
            }
        }
    }
}