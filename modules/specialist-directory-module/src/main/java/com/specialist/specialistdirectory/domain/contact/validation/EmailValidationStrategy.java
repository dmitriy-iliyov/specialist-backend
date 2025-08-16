package com.specialist.specialistdirectory.domain.contact.validation;

import com.specialist.specialistdirectory.domain.contact.ContactType;
import org.springframework.stereotype.Component;

import java.util.regex.Pattern;

@Component
public class EmailValidationStrategy implements ContactValidationStrategy {

    private final Pattern pattern = Pattern.compile("[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}");

    @Override
    public ContactType getType() {
        return ContactType.EMAIL;
    }

    @Override
    public boolean validate(String contact) {
        return pattern.matcher(contact).matches();
    }
}
