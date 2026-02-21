package com.specialist.specialistdirectory.domain.type.services;

import com.specialist.specialistdirectory.domain.language.Language;
import com.specialist.specialistdirectory.domain.type.TranslateMapper;
import com.specialist.specialistdirectory.domain.type.TranslateRepository;
import com.specialist.specialistdirectory.domain.type.models.TranslateEntity;
import com.specialist.specialistdirectory.domain.type.models.TypeEntity;
import com.specialist.specialistdirectory.domain.type.models.dtos.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TranslateServiceImplUnitTests {

    @Mock
    private TranslateRepository repository;

    @Mock
    private TranslateMapper mapper;

    @InjectMocks
    private TranslateServiceImpl service;

    @Test
    @DisplayName("UT: save() should map, set type and save")
    void save_shouldSave() {
        TypeEntity type = new TypeEntity();
        TranslateCreateDto dto = new TranslateCreateDto(Language.EN, "translate");
        TranslateEntity entity = new TranslateEntity();
        TranslateResponseDto responseDto = new TranslateResponseDto(1L, 1L, Language.EN, "translate");

        when(mapper.toEntity(dto)).thenReturn(entity);
        when(repository.save(entity)).thenReturn(entity);
        when(mapper.toDto(entity)).thenReturn(responseDto);

        TranslateResponseDto result = service.save(type, dto);

        assertEquals(responseDto, result);
        assertEquals(type, entity.getType());
        verify(repository).save(entity);
    }

    @Test
    @DisplayName("UT: saveAll() should map list, set type and save all")
    void saveAll_shouldSaveAll() {
        TypeEntity type = new TypeEntity();
        List<CompositeTranslateCreateDto> dtoList = List.of(new CompositeTranslateCreateDto(Language.EN, "translate"));
        TranslateEntity entity = new TranslateEntity();
        List<TranslateEntity> entities = List.of(entity);
        List<TranslateResponseDto> responseDtos = List.of(new TranslateResponseDto(1L, 1L, Language.EN, "translate"));

        when(mapper.toEntityList(dtoList)).thenReturn(entities);
        when(repository.saveAll(entities)).thenReturn(entities);
        when(mapper.toDtoList(entities)).thenReturn(responseDtos);

        List<TranslateResponseDto> result = service.saveAll(type, dtoList);

        assertEquals(responseDtos, result);
        assertEquals(type, entity.getType());
        verify(repository).saveAll(entities);
    }

    @Test
    @DisplayName("UT: update() should find, map and save")
    void update_shouldUpdate() {
        TranslateUpdateDto dto = new TranslateUpdateDto(Language.EN, "newTranslate");
        dto.setId(1L);
        dto.setTypeId(1L);

        TranslateEntity entity = new TranslateEntity();

        when(repository.findByTypeId(1L)).thenReturn(Optional.of(entity));
        when(repository.save(entity)).thenReturn(entity);
        when(mapper.toDto(entity)).thenReturn(new TranslateResponseDto(1L, 1L, Language.EN, "newTranslate"));

        service.update(dto);

        verify(mapper).updateEntityFromDto(dto, entity);
        verify(repository).save(entity);
    }

    @Test
    @DisplayName("UT: findAllApprovedAsJson() should return list of short dtos")
    void findAllApprovedAsJson_shouldReturnList() {
        TypeEntity type = new TypeEntity();
        type.setId(1L);
        TranslateEntity entity = new TranslateEntity();
        entity.setType(type);
        entity.setTranslate("translate");

        when(repository.findAllByLanguage(Language.EN)).thenReturn(List.of(entity));

        List<ShortTypeResponseDto> result = service.findAllApprovedAsJson(Language.EN);

        assertEquals(1, result.size());
        assertEquals(1L, result.get(0).id());
        assertEquals("translate", result.get(0).title());
    }

    @Test
    @DisplayName("UT: findAllApprovedAsMap() should return map")
    void findAllApprovedAsMap_shouldReturnMap() {
        TypeEntity type = new TypeEntity();
        type.setId(1L);
        TranslateEntity entity = new TranslateEntity();
        entity.setType(type);
        entity.setTranslate("translate");

        when(repository.findAllByLanguage(Language.EN)).thenReturn(List.of(entity));

        Map<Long, String> result = service.findAllApprovedAsMap(Language.EN);

        assertEquals(1, result.size());
        assertEquals("translate", result.get(1L));
    }

    @Test
    @DisplayName("UT: findAllByIdIn() should return grouped map")
    void findAllByIdIn_shouldReturnGroupedMap() {
        Set<Long> ids = Set.of(1L);
        TranslateEntity entity = new TranslateEntity();
        TranslateResponseDto dto = new TranslateResponseDto(1L, 1L, Language.EN, "translate");

        when(repository.findAllByTypeIdIn(ids)).thenReturn(List.of(entity));
        when(mapper.toDtoList(List.of(entity))).thenReturn(List.of(dto));

        Map<Long, List<TranslateResponseDto>> result = service.findAllByIdIn(ids);

        assertEquals(1, result.size());
        assertEquals(dto, result.get(1L).get(0));
    }

    @Test
    @DisplayName("UT: deleteById() should delete")
    void deleteById_shouldDelete() {
        service.deleteById(1L, 2L);
        verify(repository).deleteById(1L);
    }
}
