package com.specialist.specialistdirectory.domain.type.validation;

import com.specialist.specialistdirectory.domain.type.models.dtos.CompositeTranslateUpdateDto;
import com.specialist.specialistdirectory.domain.type.models.dtos.FullTypeUpdateDto;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class SynchronizedTypeIdValidator implements ConstraintValidator<SynchronizedTypeId, FullTypeUpdateDto> {

    @Override
    public boolean isValid(FullTypeUpdateDto dto, ConstraintValidatorContext context) {
        if (dto != null && dto.type().getId() != null && !dto.translates().isEmpty()) {
            Long id = dto.type().getId();
            CompositeTranslateUpdateDto notRelatedDto = dto.translates().stream()
                    .filter(translate -> !translate.getTypeId().equals(id))
                    .findFirst()
                    .orElse(null);
            if (notRelatedDto != null) {
                context.disableDefaultConstraintViolation();
                context.buildConstraintViolationWithTemplate("Translate is not related to type.")
                        .addPropertyNode("translates")
                        .addConstraintViolation();
                return false;
            }
        }
        return true;
    }
}
