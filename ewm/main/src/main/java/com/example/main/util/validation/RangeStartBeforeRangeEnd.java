package com.example.main.util.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target(METHOD)
@Retention(RUNTIME)
@Constraint(validatedBy = RangeStartBeforeRangeEndValidator.class)
public @interface RangeStartBeforeRangeEnd {
    String message() default "rangeStart must be before rangeEnd";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
