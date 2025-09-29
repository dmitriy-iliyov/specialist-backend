package com.specialist.specialistdirectory.domain.specialist.validation;

import com.specialist.contracts.specialistdirectory.dto.ContactType;

public interface ContactValidationStrategy {
    ContactType getType();
    boolean validate(String contact);
}
