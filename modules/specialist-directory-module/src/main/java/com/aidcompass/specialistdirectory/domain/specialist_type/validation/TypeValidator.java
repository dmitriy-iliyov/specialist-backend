package com.aidcompass.specialistdirectory.domain.specialist_type.validation;

import jakarta.validation.ConstraintValidatorContext;

import java.util.regex.Pattern;

public class TypeValidator {

    private final static Pattern REPEATABLE_LETTERS = Pattern.compile("(.)\\1{2,}");
    private final static Pattern ONLY_NON_WORDS = Pattern.compile("^[^\\w]+$");


    public static void validate(String typeTitle, ConstraintValidatorContext context) {
        if (typeTitle.length() < 3) {
            context.buildConstraintViolationWithTemplate("Type is to short.")
                    .addPropertyNode("another_type")
                    .addConstraintViolation();
        }

        if (typeTitle.length() > 30) {
            context.buildConstraintViolationWithTemplate("Type is to long.")
                    .addPropertyNode("another_type")
                    .addConstraintViolation();
        }

        if (REPEATABLE_LETTERS.matcher(typeTitle).matches() || ONLY_NON_WORDS.matcher(typeTitle).matches()) {
            context.buildConstraintViolationWithTemplate("Type contains invalid characters.")
                    .addPropertyNode("another_type")
                    .addConstraintViolation();
        }
    }
}
