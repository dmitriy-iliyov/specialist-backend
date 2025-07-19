package com.aidcompass.specialistdirectory.domain.type.validation;

import com.aidcompass.specialistdirectory.domain.translate.models.dtos.CompositeTranslateCreateDto;
import com.aidcompass.specialistdirectory.domain.type.validation.annotation.TranslateList;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.List;

public class TranslateCreateListValidation implements ConstraintValidator<TranslateList, List<CompositeTranslateCreateDto>> {
    @Override
    public boolean isValid(List<CompositeTranslateCreateDto> compositeTranslateCreateDtos, ConstraintValidatorContext constraintValidatorContext) {
        return false;
    }
}
