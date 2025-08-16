package com.specialist.specialistdirectory.domain.contact.validation;

import com.specialist.specialistdirectory.domain.contact.ContactType;

public interface ContactValidationStrategy {
    ContactType getType();
    boolean validate(String contact);
}
