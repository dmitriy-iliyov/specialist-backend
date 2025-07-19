package com.aidcompass.specialistdirectory.domain.type.validation;

import com.aidcompass.specialistdirectory.domain.translate.models.dtos.CompositeTranslateUpdateDto;
import com.aidcompass.specialistdirectory.domain.type.validation.annotation.TranslateList;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class TranslateUpdateListValidation implements ConstraintValidator<TranslateList, List<CompositeTranslateUpdateDto>> {

    @Override
    public boolean isValid(List<CompositeTranslateUpdateDto> dtoList, ConstraintValidatorContext context) {

        Set<CompositeTranslateUpdateDto> dtoSet = new HashSet<>(dtoList);

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
