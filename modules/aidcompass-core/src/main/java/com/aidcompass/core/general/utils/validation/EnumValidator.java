package com.aidcompass.core.general.utils.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

public class EnumValidator implements ConstraintValidator<ValidEnum, String> {

    private Set<String> validTypes;
    private boolean nullable;

    @Override
    public void initialize(ValidEnum annotation) {
        ConstraintValidator.super.initialize(annotation);
        nullable = annotation.nullable();
        validTypes = Arrays.stream(annotation.enumClass().getEnumConstants())
                .map(Enum::toString)
                .collect(Collectors.toSet());
    }

    @Override
    public boolean isValid(String type, ConstraintValidatorContext constraintValidatorContext) {
        if ((type == null || type.isBlank() || type.isEmpty()) && nullable) {
            return true;
        }
        return type != null && validTypes.contains(type);
    }
}
