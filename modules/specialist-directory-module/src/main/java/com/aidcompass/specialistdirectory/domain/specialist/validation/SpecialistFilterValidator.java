package com.aidcompass.specialistdirectory.domain.specialist.validation;

import com.aidcompass.specialistdirectory.domain.specialist.models.markers.BaseSpecialistFilter;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class SpecialistFilterValidator implements ConstraintValidator<SpecialistFilter, BaseSpecialistFilter> {


    @Override
    public boolean isValid(BaseSpecialistFilter filter, ConstraintValidatorContext context) {
        boolean hasErrors = false;

        context.disableDefaultConstraintViolation();

        if (filter.minRating() != null && filter.maxRating() != null) {
            if (filter.minRating() > filter.maxRating()) { // ИСПРАВЛЕНО: > вместо
                hasErrors = true;
                context.buildConstraintViolationWithTemplate("Min rating cannot be greater than max rating.")
                        .addPropertyNode("minRating")
                        .addConstraintViolation();
            }
        }

        boolean cityBlank = filter.city() == null || filter.city().isBlank();
        boolean cityCodeBlank = filter.cityCode() == null || filter.cityCode().isBlank();

        if (cityBlank && cityCodeBlank) {
            hasErrors = true;
            context.buildConstraintViolationWithTemplate("Either city or city code must be provided.")
                    .addPropertyNode("city")
                    .addConstraintViolation();
        }

        return !hasErrors;
    }
}