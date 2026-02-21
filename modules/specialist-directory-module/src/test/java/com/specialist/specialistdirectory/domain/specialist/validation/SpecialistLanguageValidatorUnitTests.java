package com.specialist.specialistdirectory.domain.specialist.validation;

import com.specialist.specialistdirectory.domain.specialist.models.enums.SpecialistLanguage;
import jakarta.validation.ConstraintValidatorContext;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SpecialistLanguageValidatorUnitTests {

    @InjectMocks
    private SpecialistLanguageValidator validator;

    @Test
    @DisplayName("UT: isValid() when language is null should return false")
    void isValid_null_shouldReturnFalse() {
        ConstraintValidatorContext context = mock(ConstraintValidatorContext.class);
        ConstraintValidatorContext.ConstraintViolationBuilder builder = mock(ConstraintValidatorContext.ConstraintViolationBuilder.class);
        ConstraintValidatorContext.ConstraintViolationBuilder.NodeBuilderCustomizableContext nodeBuilder = mock(ConstraintValidatorContext.ConstraintViolationBuilder.NodeBuilderCustomizableContext.class);

        when(context.buildConstraintViolationWithTemplate(anyString())).thenReturn(builder);
        when(builder.addPropertyNode(anyString())).thenReturn(nodeBuilder);

        assertFalse(validator.isValid(null, context));
    }

    @Test
    @DisplayName("UT: isValid() when language is not null should return true")
    void isValid_notNull_shouldReturnTrue() {
        ConstraintValidatorContext context = mock(ConstraintValidatorContext.class);
        assertTrue(validator.isValid(SpecialistLanguage.EN, context));
    }
}
