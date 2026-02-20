package com.specialist.specialistdirectory.ut.domain.type.validation;

import com.specialist.specialistdirectory.domain.language.Language;
import com.specialist.specialistdirectory.domain.type.models.dtos.CompositeTranslateUpdateDto;
import com.specialist.specialistdirectory.domain.type.models.dtos.FullTypeUpdateDto;
import com.specialist.specialistdirectory.domain.type.models.dtos.TypeUpdateDto;
import com.specialist.specialistdirectory.domain.type.validation.SynchronizedTypeIdValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SynchronizedTypeIdValidatorUnitTests {

    @InjectMocks
    private SynchronizedTypeIdValidator validator;

    @Test
    @DisplayName("UT: isValid() when IDs match should return true")
    void isValid_match_shouldReturnTrue() {
        TypeUpdateDto typeDto = new TypeUpdateDto("title", true);
        typeDto.setId(1L);
        CompositeTranslateUpdateDto translateDto = new CompositeTranslateUpdateDto(1L, Language.EN, "title");
        translateDto.setTypeId(1L);
        FullTypeUpdateDto dto = new FullTypeUpdateDto(typeDto, List.of(translateDto));

        ConstraintValidatorContext context = mock(ConstraintValidatorContext.class);

        assertTrue(validator.isValid(dto, context));
    }

    @Test
    @DisplayName("UT: isValid() when IDs mismatch should return false")
    void isValid_mismatch_shouldReturnFalse() {
        TypeUpdateDto typeDto = new TypeUpdateDto("title", true);
        typeDto.setId(1L);
        CompositeTranslateUpdateDto translateDto = new CompositeTranslateUpdateDto(1L, Language.EN, "title");
        translateDto.setTypeId(2L);
        FullTypeUpdateDto dto = new FullTypeUpdateDto(typeDto, List.of(translateDto));

        ConstraintValidatorContext context = mock(ConstraintValidatorContext.class);
        ConstraintValidatorContext.ConstraintViolationBuilder builder = mock(ConstraintValidatorContext.ConstraintViolationBuilder.class);
        ConstraintValidatorContext.ConstraintViolationBuilder.NodeBuilderCustomizableContext nodeBuilder = mock(ConstraintValidatorContext.ConstraintViolationBuilder.NodeBuilderCustomizableContext.class);

        when(context.buildConstraintViolationWithTemplate(anyString())).thenReturn(builder);
        when(builder.addPropertyNode(anyString())).thenReturn(nodeBuilder);

        assertFalse(validator.isValid(dto, context));
    }

    @Test
    @DisplayName("UT: isValid() when dto null should return true")
    void isValid_nullDto_shouldReturnTrue() {
        assertTrue(validator.isValid(null, null));
    }

    @Test
    @DisplayName("UT: isValid() when type id null should return true")
    void isValid_nullTypeId_shouldReturnTrue() {
        TypeUpdateDto typeDto = new TypeUpdateDto("title", true);
        FullTypeUpdateDto dto = new FullTypeUpdateDto(typeDto, Collections.emptyList());
        assertTrue(validator.isValid(dto, null));
    }

    @Test
    @DisplayName("UT: isValid() when translates empty should return true")
    void isValid_emptyTranslates_shouldReturnTrue() {
        TypeUpdateDto typeDto = new TypeUpdateDto("title", true);
        typeDto.setId(1L);
        FullTypeUpdateDto dto = new FullTypeUpdateDto(typeDto, Collections.emptyList());
        assertTrue(validator.isValid(dto, null));
    }
}
