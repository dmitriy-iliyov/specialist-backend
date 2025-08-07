package com.specialist.specialistdirectory.domain.type.validation;

import jakarta.validation.ConstraintValidatorContext;

import java.util.regex.Pattern;

public class TypeValidator {

    private final static Pattern REPEATABLE_LETTERS = Pattern.compile("(.)\\1{2,}");
    private final static Pattern ONLY_NON_WORDS = Pattern.compile("^[^\\w]+$");


    public static boolean validate(String typeTitle, ConstraintValidatorContext context) {

        boolean hasErrors = false;

        context.disableDefaultConstraintViolation();

        if (REPEATABLE_LETTERS.matcher(typeTitle).find()) {
            hasErrors = true;
            context.buildConstraintViolationWithTemplate("Type cannot contain 3 or more consecutive identical characters.")
                    .addPropertyNode("another_type")
                    .addConstraintViolation();
        }

        if (ONLY_NON_WORDS.matcher(typeTitle).matches()) {
            hasErrors = true;
            context.buildConstraintViolationWithTemplate("Type must contain at least one letter or digit.")
                    .addPropertyNode("another_type")
                    .addConstraintViolation();
        }

        return !hasErrors;
    }
}
