package com.aidcompass.specialistdirectory.domain.specialist_type.validation;

import com.aidcompass.core.general.exceptions.models.BaseNotFoundException;
import com.aidcompass.specialistdirectory.domain.specialist_type.models.dtos.ShortTypeDto;
import com.aidcompass.specialistdirectory.domain.specialist_type.models.dtos.TypeUpdateDto;
import com.aidcompass.specialistdirectory.domain.specialist_type.services.interfases.TypeService;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class UniqueTypeUpdateValidator implements ConstraintValidator<UniqueType, TypeUpdateDto> {

    private final TypeService service;


    @Override
    public boolean isValid(TypeUpdateDto typeUpdateDto, ConstraintValidatorContext constraintValidatorContext) {
        try {
            ShortTypeDto dto = service.findByTitle(typeUpdateDto.getTitle());
            if (!dto.id().equals(typeUpdateDto.getId())) {
                constraintValidatorContext.disableDefaultConstraintViolation();
                constraintValidatorContext.buildConstraintViolationWithTemplate("Type already exists.")
                        .addPropertyNode("type")
                        .addConstraintViolation();
                return false;
            }
        } catch (BaseNotFoundException ignored) {}
        return true;
    }
}
