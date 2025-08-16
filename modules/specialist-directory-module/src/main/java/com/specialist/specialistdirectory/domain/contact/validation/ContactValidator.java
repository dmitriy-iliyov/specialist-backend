package com.specialist.specialistdirectory.domain.contact.validation;

import com.specialist.specialistdirectory.domain.contact.ContactDto;
import com.specialist.specialistdirectory.domain.contact.ContactType;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;


public class ContactValidator implements ConstraintValidator<Contact, ContactDto> {

    private final Map<ContactType, ContactValidationStrategy> contactValidators;

    public ContactValidator(List<ContactValidationStrategy> contactValidatorStrategies) {
        this.contactValidators = contactValidatorStrategies.stream().collect(Collectors.toMap(ContactValidationStrategy::getType, Function.identity()));
    }

    @Override
    public boolean isValid(ContactDto contact, ConstraintValidatorContext context) {
        context.disableDefaultConstraintViolation();
        ContactValidationStrategy contactValidationStrategy = contactValidators.get(contact.type());
        if (contactValidationStrategy == null) {
            context.buildConstraintViolationWithTemplate("Unsupported contact type.")
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
