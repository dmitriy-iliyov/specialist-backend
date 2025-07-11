package com.aidcompass.core.contact.core.validation.validators;

import com.aidcompass.contracts.ContactType;

public interface FormatValidator {

    boolean isEmailValid(String email);

    boolean isLengthValid(ContactType type, String contact);

    boolean isPhoneNumberValid(String phoneNumber);
}
