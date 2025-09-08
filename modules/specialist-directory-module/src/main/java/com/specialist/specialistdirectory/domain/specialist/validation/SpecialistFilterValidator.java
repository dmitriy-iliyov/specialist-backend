package com.specialist.specialistdirectory.domain.specialist.validation;

import com.specialist.specialistdirectory.domain.specialist.models.filters.BaseSpecialistFilter;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class SpecialistFilterValidator implements ConstraintValidator<SpecialistFilter, BaseSpecialistFilter> {

    @Override
    public boolean isValid(BaseSpecialistFilter filter, ConstraintValidatorContext context) {
        boolean hasErrors = false;

        context.disableDefaultConstraintViolation();

        if (filter.isEmpty()) {
            return true;
        }

        if (filter.getMinRating() != null && filter.getMaxRating() != null) {
            if (filter.getMinRating() > filter.getMaxRating()) {
                hasErrors = true;
                context.buildConstraintViolationWithTemplate("Min rating cannot be greater than max rating.")
                        .addPropertyNode("minRating")
                        .addConstraintViolation();
            }
        }

        boolean cityBlank = filter.getCity() == null || filter.getCity().isBlank();
        boolean cityCodeBlank = filter.getCityCode() == null || filter.getCityCode().isBlank();

        if (cityBlank && cityCodeBlank) {
            hasErrors = true;
            context.buildConstraintViolationWithTemplate("Either city or city code must be provided.")
                    .addPropertyNode("city")
                    .addConstraintViolation();
        }

        return !hasErrors;
    }
}