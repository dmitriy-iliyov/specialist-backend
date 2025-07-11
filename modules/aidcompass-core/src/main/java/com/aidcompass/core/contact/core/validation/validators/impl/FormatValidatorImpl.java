package com.aidcompass.core.contact.core.validation.validators.impl;

import com.aidcompass.contracts.ContactType;
import com.aidcompass.core.contact.core.validation.validators.FormatValidator;
import org.springframework.stereotype.Component;

import java.util.regex.Pattern;

@Component
public class FormatValidatorImpl implements FormatValidator {

    private final static short MIN_EMAIL_LENGTH = 7;
    private final static short MAX_EMAIL_LENGTH = 50;
    private final Pattern emailRegx = Pattern.compile("[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}");
    private final Pattern phoneNumberRegx = Pattern.compile("^[+]\\d{12}$");


    @Override
    public boolean isEmailValid(String email) {
        return emailRegx.matcher(email).matches();
    }

    @Override
    public boolean isLengthValid(ContactType type, String contact) {
        if (type == ContactType.EMAIL) {
            return MIN_EMAIL_LENGTH <= contact.length() && contact.length() <= MAX_EMAIL_LENGTH;
        }
        return false;
    }

    @Override
    public boolean isPhoneNumberValid(String phoneNumber) {
        return phoneNumberRegx.matcher(phoneNumber).matches();
    }
}
