package com.aidcompass.specialistdirectory.domain.specialist_type.validation;

public class TypeSanitizer {

    public static String sanitize(String title) {
        title = title.strip();
        return title;
    }
}
