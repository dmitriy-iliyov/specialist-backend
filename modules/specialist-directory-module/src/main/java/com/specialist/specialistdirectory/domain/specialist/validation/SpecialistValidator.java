package com.specialist.specialistdirectory.domain.specialist.validation;

import com.specialist.specialistdirectory.domain.specialist.models.markers.SpecialistMarker;
import com.specialist.specialistdirectory.domain.type.services.TypeConstants;
import com.specialist.specialistdirectory.domain.type.services.TypeService;
import com.specialist.specialistdirectory.domain.type.validation.TypeValidator;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class SpecialistValidator implements ConstraintValidator<Specialist, SpecialistMarker> {

    private final TypeService typeService;

    @Override
    public boolean isValid(SpecialistMarker dto, ConstraintValidatorContext context) {
        boolean hasErrors = false;

        context.disableDefaultConstraintViolation();

        if(!typeService.existsById(dto.getTypeId())) {
            context.buildConstraintViolationWithTemplate("Non-existent type id.")
                    .addPropertyNode("type_id")
                    .addConstraintViolation();
            return false;
        }

        if (dto.getTypeId().equals(TypeConstants.OTHER_TYPE_ID)) {
            String anotherType = dto.getAnotherType();
            if (anotherType == null) {
                hasErrors = true;
                context.buildConstraintViolationWithTemplate("Another type is required.")
                        .addPropertyNode("another_type")
                        .addConstraintViolation();
            } else {
                if (anotherType.isBlank()) {
                    hasErrors = true;
                    context.buildConstraintViolationWithTemplate("Another type is required.")
                            .addPropertyNode("another_type")
                            .addConstraintViolation();
                } else {
                    hasErrors |= !TypeValidator.validate(anotherType, context);
                }
            }
        }

        return !hasErrors;
    }
}