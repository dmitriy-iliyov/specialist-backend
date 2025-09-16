package com.specialist.specialistdirectory.domain.specialist.validation;

import com.specialist.contracts.specialistdirectory.dto.ContactType;
import com.specialist.specialistdirectory.domain.specialist.models.dtos.ContactDto;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Slf4j
public class ContactValidator implements ConstraintValidator<Contact, ContactDto> {

    private final Map<ContactType, ContactValidationStrategy> validationStrategyMap;

    public ContactValidator(List<ContactValidationStrategy> contactValidatorStrategies) {
        this.validationStrategyMap = contactValidatorStrategies.stream()
                .collect(Collectors.toMap(ContactValidationStrategy::getType, Function.identity()));
    }

    @Override
    public boolean isValid(ContactDto contact, ConstraintValidatorContext context) {
        context.disableDefaultConstraintViolation();
        if (contact == null) {
            context.buildConstraintViolationWithTemplate("Unsupported contact type.")
                    .addPropertyNode("type")
                    .addConstraintViolation();
            return false;
        }
        ContactValidationStrategy contactValidationStrategy = validationStrategyMap.get(contact.type());
        if (contactValidationStrategy == null) {
            log.warn("Strategy for contact validation not found but contact type: {} is supported.", contact.type());
            context.buildConstraintViolationWithTemplate("Can't validate contact of this type.")
                    .addPropertyNode("type")
                    .addConstraintViolation();
            return false;
        } else {
            if (!contactValidationStrategy.validate(contact.value())) {
                context.buildConstraintViolationWithTemplate("Contact should be valid.")
                        .addPropertyNode("value")
                        .addConstraintViolation();
                return false;
            }
        }
        return true;
    }
}
