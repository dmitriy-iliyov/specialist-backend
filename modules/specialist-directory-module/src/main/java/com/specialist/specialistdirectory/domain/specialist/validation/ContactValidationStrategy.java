package com.specialist.specialistdirectory.domain.specialist.validation;

import com.specialist.specialistdirectory.domain.specialist.models.enums.ContactType;

public interface ContactValidationStrategy {
    ContactType getType();
    boolean validate(String contact);
}
