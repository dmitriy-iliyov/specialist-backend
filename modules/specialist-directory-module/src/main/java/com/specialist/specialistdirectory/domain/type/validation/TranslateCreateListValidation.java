package com.specialist.specialistdirectory.domain.type.validation;

import com.specialist.specialistdirectory.domain.translate.models.dtos.CompositeTranslateCreateDto;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class TranslateCreateListValidation implements ConstraintValidator<TranslateList, List<CompositeTranslateCreateDto>> {
    @Override
    public boolean isValid(List<CompositeTranslateCreateDto> dtoList, ConstraintValidatorContext context) {
        Set<CompositeTranslateCreateDto> dtoSet = new HashSet<>(dtoList);

        if (dtoList.size() != dtoSet.size()) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("Some of element isn't unique.")
                    .addPropertyNode("translates")
                    .addConstraintViolation();
            return false;
        }
        return true;
    }
}
