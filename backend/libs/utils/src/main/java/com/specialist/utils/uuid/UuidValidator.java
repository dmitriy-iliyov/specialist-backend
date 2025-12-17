package com.specialist.utils.uuid;

import com.specialist.utils.uuid.annotation.ValidUuid;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.regex.Pattern;

public class UuidValidator implements ConstraintValidator<ValidUuid, String> {

    private final Pattern pattern = Pattern.compile(
            "^[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-7[0-9a-fA-F]{3}-[89abAB][0-9a-fA-F]{3}-[0-9a-fA-F]{12}$"
    );
    private String paramName;


    @Override
    public void initialize(ValidUuid constraintAnnotation) {
        this.paramName = constraintAnnotation.paramName();
    }

    @Override
    public boolean isValid(String uuid, ConstraintValidatorContext context) {
        context.disableDefaultConstraintViolation();
        if (uuid == null) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(paramName + " is required.")
                    .addConstraintViolation();
            return false;
        }
        if (!pattern.matcher(uuid).matches()) {
            context.buildConstraintViolationWithTemplate("Invalid UUID format.")
                    .addPropertyNode(paramName)
                    .addConstraintViolation();
            return false;
        }
        return true;
    }
}
