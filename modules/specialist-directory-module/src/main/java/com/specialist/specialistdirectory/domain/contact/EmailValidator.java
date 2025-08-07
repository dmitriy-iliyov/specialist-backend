package com.specialist.specialistdirectory.domain.contact;

import org.springframework.stereotype.Component;

import java.util.regex.Pattern;

@Component
public class EmailValidator implements ContactValidator {

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
