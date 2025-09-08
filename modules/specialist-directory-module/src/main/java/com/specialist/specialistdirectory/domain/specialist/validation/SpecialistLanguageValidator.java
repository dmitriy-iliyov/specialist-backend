package com.specialist.specialistdirectory.domain.specialist.validation;

import com.specialist.specialistdirectory.domain.specialist.models.enums.SpecialistLanguage;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class SpecialistLanguageValidator implements ConstraintValidator<SpecialistLang, SpecialistLanguage> {

    @Override
    public boolean isValid(SpecialistLanguage language, ConstraintValidatorContext context) {
        context.disableDefaultConstraintViolation();
        if (language == null) {
            context.buildConstraintViolationWithTemplate("Unsupported specialist language.")
                    .addPropertyNode("language")
                    .addConstraintViolation();
            return false;
        }
        return true;
    }
}
