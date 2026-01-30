package com.specialist.specialistdirectory.ut.domain.language;

import com.specialist.specialistdirectory.domain.language.Language;
import com.specialist.specialistdirectory.domain.language.LanguageConverter;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class LanguageConverterUnitTests {

    private final LanguageConverter converter = new LanguageConverter();

    @Test
    @DisplayName("UT: convertToDatabaseColumn() should return code")
    void convertToDatabaseColumn_shouldReturnCode() {
        assertEquals(Language.EN.getCode(), converter.convertToDatabaseColumn(Language.EN));
    }

    @Test
    @DisplayName("UT: convertToEntityAttribute() should return enum")
    void convertToEntityAttribute_shouldReturnEnum() {
        assertEquals(Language.EN, converter.convertToEntityAttribute(Language.EN.getCode()));
    }
}
