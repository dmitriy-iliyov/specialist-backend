package com.aidcompass.core.contact.core.validation.annotation;

import com.aidcompass.contracts.ContactType;
import com.aidcompass.core.contact.core.models.dto.ContactCreateDto;
import com.aidcompass.core.contact.core.validation.validators.FormatValidator;
import com.aidcompass.core.contact.core.validation.validators.impl.ContactUniquenessValidatorImpl;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
public class ContactCreateValidator implements ConstraintValidator<Contact, ContactCreateDto> {

    private final ContactUniquenessValidatorImpl uniquenessValidator;
    private final FormatValidator formatValidator;


    @Override
    public boolean isValid(ContactCreateDto contact, ConstraintValidatorContext context) {
        boolean hasErrors = false;

        if (contact.type() == ContactType.EMAIL) {
            if (!formatValidator.isEmailValid(contact.contact())) {
                context.buildConstraintViolationWithTemplate("Email should be valid!")
                        .addPropertyNode("contact")
                        .addConstraintViolation();
                hasErrors = true;
            }

            if (!formatValidator.isLengthValid(ContactType.EMAIL, contact.contact())) {
                context.buildConstraintViolationWithTemplate("Email length must be greater than 7 and less than 50!")
                        .addPropertyNode("contact")
                        .addConstraintViolation();
                hasErrors = true;
            }

            if (!uniquenessValidator.isEmailUnique(contact.contact())) {
                context.buildConstraintViolationWithTemplate("Email is in use!")
                        .addPropertyNode("contact")
                        .addConstraintViolation();
                hasErrors = true;
            }

        } else if (contact.type() == ContactType.PHONE_NUMBER) {
            if (!formatValidator.isPhoneNumberValid(contact.contact())) {
                context.buildConstraintViolationWithTemplate("Phone number should be valid!")
                        .addPropertyNode("contact")
                        .addConstraintViolation();
                hasErrors = true;
            }

            if (!uniquenessValidator.isPhoneNumberUnique(contact.contact())) {
                context.buildConstraintViolationWithTemplate("Phone number is in use!")
                        .addPropertyNode("contact")
                        .addConstraintViolation();
                hasErrors = true;
            }

        } else {
            context.buildConstraintViolationWithTemplate("Contact type is invalid!")
                    .addPropertyNode("type")
                    .addConstraintViolation();
            hasErrors = true;
        }

        return !hasErrors;
    }
}
