package com.specialist.specialistdirectory.domain.contact;

import org.springframework.stereotype.Component;

import java.util.regex.Pattern;

@Component
public class PhoneNumberValidator implements ContactValidator {

    private final Pattern pattern = Pattern.compile("^[+]\\d{12}$");

    @Override
    public ContactType getType() {
        return ContactType.PHONE_NUMBER;
    }

    @Override
    public boolean validate(String contact) {
        return pattern.matcher(contact).matches();
    }
}
