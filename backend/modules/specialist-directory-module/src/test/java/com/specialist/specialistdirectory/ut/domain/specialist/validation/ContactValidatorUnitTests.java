package com.specialist.specialistdirectory.ut.domain.specialist.validation;

import com.specialist.contracts.specialistdirectory.dto.ContactType;
import com.specialist.specialistdirectory.domain.specialist.models.dtos.ContactDto;
import com.specialist.specialistdirectory.domain.specialist.validation.ContactValidationStrategy;
import com.specialist.specialistdirectory.domain.specialist.validation.ContactValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class ContactValidatorUnitTests {

    @Mock
    private ContactValidationStrategy strategy;

    @Mock
    private ConstraintValidatorContext context;

    @Mock
    private ConstraintValidatorContext.ConstraintViolationBuilder builder;

    @Mock
    private ConstraintValidatorContext.ConstraintViolationBuilder.NodeBuilderCustomizableContext nodeBuilder;

    private ContactValidator validator;

    @BeforeEach
    void setUp() {
        when(strategy.getType()).thenReturn(ContactType.EMAIL);
        validator = new ContactValidator(List.of(strategy));
        lenient().when(context.buildConstraintViolationWithTemplate(anyString())).thenReturn(builder);
        lenient().when(builder.addPropertyNode(anyString())).thenReturn(nodeBuilder);
    }

    @Test
    @DisplayName("UT: isValid() when contact is valid should return true")
    void isValid_validContact_shouldReturnTrue() {
        ContactDto contact = new ContactDto(ContactType.EMAIL, "test@test.com");
        when(strategy.validate("test@test.com")).thenReturn(true);
        assertTrue(validator.isValid(contact, context));
    }

    @Test
    @DisplayName("UT: isValid() when contact is invalid should return false")
    void isValid_invalidContact_shouldReturnFalse() {
        ContactDto contact = new ContactDto(ContactType.EMAIL, "invalid");
        when(strategy.validate("invalid")).thenReturn(false);
        assertFalse(validator.isValid(contact, context));
    }

    @Test
    @DisplayName("UT: isValid() when contact is null should return false")
    void isValid_nullContact_shouldReturnFalse() {
        assertFalse(validator.isValid(null, context));
    }

    @Test
    @DisplayName("UT: isValid() when strategy not found should return false")
    void isValid_strategyNotFound_shouldReturnFalse() {
        ContactDto contact = new ContactDto(ContactType.PHONE_NUMBER, "123");
        assertFalse(validator.isValid(contact, context));
    }
}
