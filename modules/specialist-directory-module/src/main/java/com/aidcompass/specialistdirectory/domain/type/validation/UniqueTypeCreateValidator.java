package com.aidcompass.specialistdirectory.domain.type.validation;

import com.aidcompass.specialistdirectory.domain.type.models.dtos.TypeCreateDto;
import com.aidcompass.specialistdirectory.domain.type.services.interfaces.TypeService;
import com.aidcompass.specialistdirectory.domain.type.validation.annotation.UniqueType;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class UniqueTypeCreateValidator implements ConstraintValidator<UniqueType, TypeCreateDto> {

    private final TypeService service;


    @Override
    public boolean isValid(TypeCreateDto typeCreateDto, ConstraintValidatorContext constraintValidatorContext) {
        if (service.existsByTitleIgnoreCase(typeCreateDto.getTitle())) {
            constraintValidatorContext.disableDefaultConstraintViolation();
            constraintValidatorContext
                    .buildConstraintViolationWithTemplate("Type already exists.")
                    .addPropertyNode("type")
                    .addConstraintViolation();
            return false;
        }
        return true;
    }
}
