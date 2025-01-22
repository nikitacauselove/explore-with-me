package ru.practicum.ewm.util.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.constraintvalidation.SupportedValidationTarget;
import javax.validation.constraintvalidation.ValidationTarget;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@SupportedValidationTarget(ValidationTarget.PARAMETERS)
public class RangeStartBeforeRangeEndValidator implements ConstraintValidator<RangeStartBeforeRangeEnd, Object[]> {
    @Override
    public boolean isValid(Object[] arguments, ConstraintValidatorContext context) {
        List<LocalDateTime> ranges = Arrays.stream(arguments)
                .filter(LocalDateTime.class::isInstance)
                .map(LocalDateTime.class::cast)
                .collect(Collectors.toList());

        return ranges.size() < 2 || ranges.get(0).isBefore(ranges.get(1));
    }
}
