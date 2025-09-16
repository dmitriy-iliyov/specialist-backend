package com.specialist.specialistdirectory.domain.specialist.validation;

import com.specialist.contracts.specialistdirectory.dto.ContactType;
import org.springframework.stereotype.Component;

import java.util.regex.Pattern;

@Component
public class PhoneNumberValidationStrategy implements ContactValidationStrategy {

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
