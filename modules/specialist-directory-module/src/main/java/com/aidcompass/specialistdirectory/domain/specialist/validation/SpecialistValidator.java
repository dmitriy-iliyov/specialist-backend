package com.aidcompass.specialistdirectory.domain.specialist.validation;

import com.aidcompass.specialistdirectory.domain.contact.ContactType;
import com.aidcompass.specialistdirectory.domain.contact.ContactValidator;
import com.aidcompass.specialistdirectory.domain.specialist.models.markers.SpecialistMarker;
import com.aidcompass.specialistdirectory.domain.specialist.validation.annotation.Specialist;
import com.aidcompass.specialistdirectory.domain.specialist_type.services.TypeConstants;
import com.aidcompass.specialistdirectory.domain.specialist_type.services.interfases.TypeService;
import com.aidcompass.specialistdirectory.domain.specialist_type.validation.TypeValidator;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class SpecialistValidator implements ConstraintValidator<Specialist, SpecialistMarker> {

    private final Map<ContactType, ContactValidator> contactValidators;
    private final TypeService typeService;


    public SpecialistValidator(List<ContactValidator> contactValidators, TypeService typeService) {
        this.contactValidators = contactValidators.stream().collect(Collectors.toMap(ContactValidator::getType, Function.identity()));
        this.typeService = typeService;
    }

    @Override
    public boolean isValid(SpecialistMarker dto, ConstraintValidatorContext context) {
        boolean hasErrors = false;

        context.disableDefaultConstraintViolation();

        ContactValidator contactValidator = contactValidators.get(dto.getContactType());
        if (contactValidator == null) {
            hasErrors = true;
            context.buildConstraintViolationWithTemplate("Unsupported contact type.")
                    .addPropertyNode("contact_type")
                    .addConstraintViolation();
        } else {
            if (!contactValidator.validate(dto.getContact())) {
                hasErrors = true;
                context.buildConstraintViolationWithTemplate("Contact should be valid.")
                        .addPropertyNode("contact")
                        .addConstraintViolation();
            }
        }

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