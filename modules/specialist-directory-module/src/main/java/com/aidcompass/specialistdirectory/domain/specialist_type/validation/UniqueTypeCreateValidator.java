package com.aidcompass.specialistdirectory.domain.specialist_type.validation;

import com.aidcompass.specialistdirectory.domain.specialist_type.models.dtos.TypeCreateDto;
import com.aidcompass.specialistdirectory.domain.specialist_type.services.interfases.TypeService;
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
