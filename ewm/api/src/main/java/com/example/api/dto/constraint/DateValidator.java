package com.example.api.dto.constraint;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.time.LocalDateTime;

public class DateValidator implements ConstraintValidator<EventDateValidator, LocalDateTime> {

    @Override
    public void initialize(EventDateValidator constraintAnnotation) {
    }

    @Override
    public boolean isValid(LocalDateTime eventDate, ConstraintValidatorContext constraintValidatorContext) {
        LocalDateTime now = LocalDateTime.now();
        if (eventDate != null) {
            return eventDate.isAfter(now.plusHours(2L));
        }
        return true;
    }
}
