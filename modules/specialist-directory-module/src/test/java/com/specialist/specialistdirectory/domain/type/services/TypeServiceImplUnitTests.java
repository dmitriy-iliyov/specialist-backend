package com.specialist.specialistdirectory.domain.type.services;

import com.specialist.specialistdirectory.domain.type.TypeMapper;
import com.specialist.specialistdirectory.domain.type.TypeRepository;
import com.specialist.specialistdirectory.domain.type.models.TypeEntity;
import com.specialist.specialistdirectory.domain.type.models.dtos.ShortTypeResponseDto;
import com.specialist.specialistdirectory.domain.type.models.dtos.TypeCreateDto;
import com.specialist.specialistdirectory.domain.type.models.dtos.TypeResponseDto;
import com.specialist.specialistdirectory.domain.type.models.dtos.TypeUpdateDto;
import com.specialist.specialistdirectory.exceptions.NullOrBlankAnotherTypeException;
import com.specialist.specialistdirectory.exceptions.SpecialistTypeEntityNotFoundByIdException;
import com.specialist.specialistdirectory.exceptions.SpecialistTypeEntityNotFoundByTitleException;
import com.specialist.utils.pagination.PageRequest;
import com.specialist.utils.pagination.PageResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TypeServiceImplUnitTests {

    @Mock
    private TypeRepository repository;

    @Mock
    private TypeMapper mapper;

    @Mock
    private TypeCacheService cacheService;

    @InjectMocks
    private TypeServiceImpl service;

    @Test
    @DisplayName("UT: save() should map, set approved, save and cache")
    void save_shouldSaveAndCache() {
        TypeCreateDto dto = new TypeCreateDto("title");
        TypeEntity entity = new TypeEntity();
        TypeResponseDto responseDto = new TypeResponseDto(1L, UUID.randomUUID(), "title", true, LocalDateTime.now(), LocalDateTime.now());

        when(mapper.toEntity(dto)).thenReturn(entity);
        when(repository.save(entity)).thenReturn(entity);
        when(mapper.toDto(entity)).thenReturn(responseDto);

        TypeResponseDto result = service.save(dto);

        assertEquals(responseDto, result);
        assertTrue(entity.isApproved());
        verify(repository).save(entity);
        verify(cacheService).putToExists(1L);
    }

    @Test
    @DisplayName("UT: suggest() when title is null should throw exception")
    void suggest_whenTitleNull_shouldThrowException() {
        TypeCreateDto dto = new TypeCreateDto(null);
        assertThrows(NullOrBlankAnotherTypeException.class, () -> service.suggest(dto));
    }

    @Test
    @DisplayName("UT: suggest() when title is blank should throw exception")
    void suggest_whenTitleBlank_shouldThrowException() {
        TypeCreateDto dto = new TypeCreateDto("   ");
        assertThrows(NullOrBlankAnotherTypeException.class, () -> service.suggest(dto));
    }

    @Test
    @DisplayName("UT: suggest() when type exists should return id and cache")
    void suggest_whenTypeExists_shouldReturnId() {
        TypeCreateDto dto = new TypeCreateDto("title");
        TypeEntity entity = new TypeEntity();
        entity.setId(1L);

        when(repository.findByTitle("TITLE")).thenReturn(Optional.of(entity));

        Long result = service.suggest(dto);

        assertEquals(1L, result);
        verify(cacheService).putToExists(1L);
        verify(repository, never()).save(any());
    }

    @Test
    @DisplayName("UT: suggest() when type not exists should create, save and cache")
    void suggest_whenTypeNotExists_shouldCreateAndSave() {
        TypeCreateDto dto = new TypeCreateDto("title");
        TypeEntity entity = new TypeEntity();
        entity.setId(1L);
        TypeResponseDto responseDto = new TypeResponseDto(1L, UUID.randomUUID(), "title", false, LocalDateTime.now(), LocalDateTime.now());

        when(repository.findByTitle("TITLE")).thenReturn(Optional.empty());
        when(mapper.toEntity(dto)).thenReturn(entity);
        when(repository.save(entity)).thenReturn(entity);
        when(mapper.toDto(entity)).thenReturn(responseDto);

        Long result = service.suggest(dto);

        assertEquals(1L, result);
        assertFalse(entity.isApproved());
        verify(repository).save(entity);
        verify(cacheService).putToSuggestedType(responseDto);
        verify(cacheService).putToExists(1L);
    }

    @Test
    @DisplayName("UT: existsById() should call repository")
    void existsById_shouldCallRepository() {
        when(repository.existsById(1L)).thenReturn(true);
        assertTrue(service.existsById(1L));
    }

    @Test
    @DisplayName("UT: existsByTitleIgnoreCase() should call repository")
    void existsByTitleIgnoreCase_shouldCallRepository() {
        when(repository.existsByTitleIgnoreCase("title")).thenReturn(true);
        assertTrue(service.existsByTitleIgnoreCase("title"));
    }

    @Test
    @DisplayName("UT: getReferenceById() should call repository")
    void getReferenceById_shouldCallRepository() {
        TypeEntity entity = new TypeEntity();
        when(repository.getReferenceById(1L)).thenReturn(entity);
        assertEquals(entity, service.getReferenceById(1L));
    }

    @Test
    @DisplayName("UT: findSuggestedById() should return dto")
    void findSuggestedById_shouldReturnDto() {
        TypeEntity entity = new TypeEntity();
        TypeResponseDto dto = mock(TypeResponseDto.class);
        when(repository.findByIdAndIsApproved(1L, false)).thenReturn(Optional.of(entity));
        when(mapper.toDto(entity)).thenReturn(dto);

        assertEquals(dto, service.findSuggestedById(1L));
    }

    @Test
    @DisplayName("UT: findSuggestedById() when not found should throw exception")
    void findSuggestedById_notFound_shouldThrow() {
        when(repository.findByIdAndIsApproved(1L, false)).thenReturn(Optional.empty());
        assertThrows(SpecialistTypeEntityNotFoundByIdException.class, () -> service.findSuggestedById(1L));
    }

    @Test
    @DisplayName("UT: findByTitle() should return dto")
    void findByTitle_shouldReturnDto() {
        TypeEntity entity = new TypeEntity();
        ShortTypeResponseDto dto = mock(ShortTypeResponseDto.class);
        when(repository.findByTitle("TITLE")).thenReturn(Optional.of(entity));
        when(mapper.toShortDto(entity)).thenReturn(dto);

        assertEquals(dto, service.findByTitle("title"));
    }

    @Test
    @DisplayName("UT: findByTitle() when not found should throw exception")
    void findByTitle_notFound_shouldThrow() {
        when(repository.findByTitle("TITLE")).thenReturn(Optional.empty());
        assertThrows(SpecialistTypeEntityNotFoundByTitleException.class, () -> service.findByTitle("title"));
    }

    @Test
    @DisplayName("UT: findAll() should return mapped PageResponse")
    void findAll_shouldReturnMappedPage() {
        PageRequest pageRequest = new PageRequest(0, 10, true);
        TypeEntity entity = new TypeEntity();
        Page<TypeEntity> entityPage = new PageImpl<>(List.of(entity));
        TypeResponseDto dto = new TypeResponseDto(1L, UUID.randomUUID(), "title", true, LocalDateTime.now(), LocalDateTime.now());

        when(repository.findAll(any(Pageable.class))).thenReturn(entityPage);
        when(mapper.toDtoList(List.of(entity))).thenReturn(List.of(dto));

        PageResponse<TypeResponseDto> result = service.findAll(pageRequest);

        assertEquals(1, result.data().size());
        assertEquals(dto, result.data().get(0));
        assertEquals(1, result.totalPages());
    }

    @Test
    @DisplayName("UT: findAllApprovedIds() should call repository")
    void findAllApprovedIds_shouldCallRepository() {
        List<Long> ids = List.of(1L, 2L);
        when(repository.findAllIdByIsApproved(true)).thenReturn(ids);
        assertEquals(ids, service.findAllApprovedIds());
    }

    @Test
    @DisplayName("UT: findAllUnapproved() should return mapped PageResponse")
    void findAllUnapproved_shouldReturnMappedPage() {
        PageRequest pageRequest = new PageRequest(0, 10, true);
        TypeEntity entity = new TypeEntity();
        Page<TypeEntity> entityPage = new PageImpl<>(List.of(entity));
        TypeResponseDto dto = mock(TypeResponseDto.class);

        when(repository.findAllByIsApproved(eq(false), any(Pageable.class))).thenReturn(entityPage);
        when(mapper.toDtoList(List.of(entity))).thenReturn(List.of(dto));

        PageResponse<TypeResponseDto> result = service.findAllUnapproved(pageRequest);

        assertEquals(1, result.data().size());
        assertEquals(dto, result.data().get(0));
    }

    @Test
    @DisplayName("UT: approve() when type exists should set approved, save and evict cache")
    void approve_whenTypeExists_shouldApprove() {
        Long id = 1L;
        TypeEntity entity = new TypeEntity();
        entity.setId(id);
        entity.setTitle("title");

        when(repository.findById(id)).thenReturn(Optional.of(entity));
        when(repository.save(entity)).thenReturn(entity);

        String result = service.approve(id);

        assertEquals("title", result);
        assertTrue(entity.isApproved());
        verify(repository).save(entity);
        verify(cacheService).evictSuggestedType(id);
    }

    @Test
    @DisplayName("UT: approve() when type not found should throw exception")
    void approve_whenTypeNotFound_shouldThrowException() {
        Long id = 1L;
        when(repository.findById(id)).thenReturn(Optional.empty());

        assertThrows(SpecialistTypeEntityNotFoundByIdException.class, () -> service.approve(id));
        verify(repository, never()).save(any());
    }

    @Test
    @DisplayName("UT: update() when type exists should update, save and evict cache")
    void update_whenTypeExists_shouldUpdate() {
        TypeUpdateDto dto = new TypeUpdateDto("newTitle", false);
        dto.setId(1L);
        TypeEntity entity = new TypeEntity();
        entity.setId(1L);
        entity.setTitle("oldTitle");
        TypeResponseDto responseDto = new TypeResponseDto(1L, UUID.randomUUID(), "newTitle", true, LocalDateTime.now(), LocalDateTime.now());

        when(repository.findById(1L)).thenReturn(Optional.of(entity));
        when(repository.save(entity)).thenReturn(entity);
        when(mapper.toDto(entity)).thenReturn(responseDto);

        TypeResponseDto result = service.update(dto);

        assertEquals(responseDto, result);
        verify(mapper).updateEntityFromDto(dto, entity);
        verify(repository).save(entity);
        verify(cacheService).evictSuggestedTypeId("oldTitle", true);
    }

    @Test
    @DisplayName("UT: update() when type not found should throw exception")
    void update_notFound_shouldThrow() {
        TypeUpdateDto dto = new TypeUpdateDto("newTitle", false);
        dto.setId(1L);
        when(repository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(SpecialistTypeEntityNotFoundByIdException.class, () -> service.update(dto));
    }

    @Test
    @DisplayName("UT: deleteById() should delete and evict cache")
    void deleteById_shouldDeleteAndEvict() {
        Long id = 1L;
        service.deleteById(id);
        verify(repository).deleteById(id);
        verify(cacheService).totalEvictSuggestedType(id);
    }
}