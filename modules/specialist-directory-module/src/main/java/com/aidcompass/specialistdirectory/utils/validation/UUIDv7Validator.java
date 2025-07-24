package com.aidcompass.specialistdirectory.utils.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.UUID;
import java.util.regex.Pattern;

public class UUIDv7Validator implements ConstraintValidator<UUIDv7, UUID> {

    private final Pattern pattern = Pattern.compile(
            "^[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-7[0-9a-fA-F]{3}-[89abAB][0-9a-fA-F]{3}-[0-9a-fA-F]{12}$"
    );
    private String paramName;

    @Override
    public void initialize(UUIDv7 constraintAnnotation) {
        this.paramName = constraintAnnotation.paramName();
    }

    @Override
    public boolean isValid(UUID uuid, ConstraintValidatorContext constraintValidatorContext) {
        constraintValidatorContext.disableDefaultConstraintViolation();
        if (uuid == null) {
            constraintValidatorContext.buildConstraintViolationWithTemplate(paramName + " is required.")
                    .addPropertyNode(paramName)
                    .addConstraintViolation();
            return false;
        }
        if (!pattern.matcher(uuid.toString()).matches()) {
            constraintValidatorContext.buildConstraintViolationWithTemplate("Invalid UUID format.")
                    .addPropertyNode(paramName)
                    .addConstraintViolation();
            return false;
        }
        return true;
    }
}
