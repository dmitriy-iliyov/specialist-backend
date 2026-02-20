package com.specialist.specialistdirectory.ut.domain.type.services;

import com.specialist.specialistdirectory.domain.language.Language;
import com.specialist.specialistdirectory.domain.type.models.TypeEntity;
import com.specialist.specialistdirectory.domain.type.models.dtos.*;
import com.specialist.specialistdirectory.domain.type.services.TranslateService;
import com.specialist.specialistdirectory.domain.type.services.TypeOrchestratorImpl;
import com.specialist.specialistdirectory.domain.type.services.TypeService;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TypeOrchestratorImplUnitTests {

    @Mock
    private TypeService service;
    @Mock
    private TranslateService translateService;
    @Mock
    private Validator validator;

    @InjectMocks
    private TypeOrchestratorImpl orchestrator;

    @Test
    @DisplayName("UT: save() should save type and translates")
    void save_shouldSave() {
        UUID creatorId = UUID.randomUUID();
        TypeCreateDto typeDto = new TypeCreateDto("title");
        List<CompositeTranslateCreateDto> translates = Collections.emptyList();
        FullTypeCreateDto dto = new FullTypeCreateDto(typeDto, translates);
        TypeResponseDto responseDto = mock(TypeResponseDto.class);
        when(responseDto.id()).thenReturn(1L);
        TypeEntity entity = new TypeEntity();

        when(service.save(typeDto)).thenReturn(responseDto);
        when(service.getReferenceById(1L)).thenReturn(entity);
        when(translateService.saveAll(entity, translates)).thenReturn(Collections.emptyList());

        FullTypeResponseDto result = orchestrator.save(creatorId, dto);

        assertEquals(responseDto, result.type());
        assertEquals(creatorId, typeDto.getCreatorId());
        verify(service).save(typeDto);
        verify(translateService).saveAll(entity, translates);
    }

    @Test
    @DisplayName("UT: update() when valid should update type and translates")
    void update_valid_shouldUpdate() {
        Long typeId = 1L;
        TypeUpdateDto typeDto = new TypeUpdateDto("title", true);
        List<CompositeTranslateUpdateDto> translates = List.of(new CompositeTranslateUpdateDto(1L, Language.EN, "title"));
        FullTypeUpdateDto dto = new FullTypeUpdateDto(typeDto, translates);
        TypeResponseDto responseDto = mock(TypeResponseDto.class);
        TypeEntity entity = new TypeEntity();

        when(validator.validate(dto)).thenReturn(Collections.emptySet());
        when(service.update(typeDto)).thenReturn(responseDto);
        when(service.getReferenceById(typeId)).thenReturn(entity);
        when(translateService.updateAll(entity, translates)).thenReturn(Collections.emptyList());

        FullTypeResponseDto result = orchestrator.update(typeId, dto);

        assertEquals(responseDto, result.type());
        assertEquals(typeId, typeDto.getId());
        assertEquals(typeId, translates.get(0).getTypeId());
        verify(service).update(typeDto);
        verify(translateService).updateAll(entity, translates);
    }

    @Test
    @DisplayName("UT: update() when invalid should throw exception")
    void update_invalid_shouldThrow() {
        Long typeId = 1L;
        FullTypeUpdateDto dto = new FullTypeUpdateDto(new TypeUpdateDto("title", true), Collections.emptyList());
        ConstraintViolation<FullTypeUpdateDto> violation = mock(ConstraintViolation.class);

        when(validator.validate(dto)).thenReturn(Set.of(violation));

        assertThrows(ConstraintViolationException.class, () -> orchestrator.update(typeId, dto));
    }

    @Test
    @DisplayName("UT: deleteById() should delegate")
    void deleteById_shouldDelegate() {
        orchestrator.deleteById(1L);
        verify(service).deleteById(1L);
    }
}
