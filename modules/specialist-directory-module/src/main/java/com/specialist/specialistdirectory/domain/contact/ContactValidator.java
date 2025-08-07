package com.specialist.specialistdirectory.domain.contact;

public interface ContactValidator {
    ContactType getType();
    boolean validate(String contact);
}
