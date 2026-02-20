package com.specialist.specialistdirectory.ut.domain.type.validation;

import com.specialist.specialistdirectory.domain.language.Language;
import com.specialist.specialistdirectory.domain.type.models.dtos.CompositeTranslateUpdateDto;
import com.specialist.specialistdirectory.domain.type.validation.TranslateUpdateListValidation;
import jakarta.validation.ConstraintValidatorContext;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TranslateUpdateListValidationUnitTests {

    @InjectMocks
    private TranslateUpdateListValidation validator;

    @Test
    @DisplayName("UT: isValid() when duplicates exist should return false")
    void isValid_duplicates_shouldReturnFalse() {
        CompositeTranslateUpdateDto dto1 = new CompositeTranslateUpdateDto(1L, Language.EN, "title");
        CompositeTranslateUpdateDto dto2 = new CompositeTranslateUpdateDto(1L, Language.EN, "title");
        List<CompositeTranslateUpdateDto> list = List.of(dto1, dto2);

        ConstraintValidatorContext context = mock(ConstraintValidatorContext.class);
        ConstraintValidatorContext.ConstraintViolationBuilder builder = mock(ConstraintValidatorContext.ConstraintViolationBuilder.class);
        ConstraintValidatorContext.ConstraintViolationBuilder.NodeBuilderCustomizableContext nodeBuilder = mock(ConstraintValidatorContext.ConstraintViolationBuilder.NodeBuilderCustomizableContext.class);

        when(context.buildConstraintViolationWithTemplate(anyString())).thenReturn(builder);
        when(builder.addPropertyNode(anyString())).thenReturn(nodeBuilder);

        assertFalse(validator.isValid(list, context));
    }

    @Test
    @DisplayName("UT: isValid() when unique should return true")
    void isValid_unique_shouldReturnTrue() {
        CompositeTranslateUpdateDto dto1 = new CompositeTranslateUpdateDto(1L, Language.EN, "title");
        CompositeTranslateUpdateDto dto2 = new CompositeTranslateUpdateDto(2L, Language.UA, "title");
        List<CompositeTranslateUpdateDto> list = List.of(dto1, dto2);

        ConstraintValidatorContext context = mock(ConstraintValidatorContext.class);

        assertTrue(validator.isValid(list, context));
    }
}
