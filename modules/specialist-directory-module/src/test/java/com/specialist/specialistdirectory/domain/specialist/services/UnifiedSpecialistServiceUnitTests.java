package com.specialist.specialistdirectory.domain.specialist.services;

import com.specialist.specialistdirectory.domain.specialist.mappers.SpecialistMapper;
import com.specialist.specialistdirectory.domain.specialist.models.SpecialistEntity;
import com.specialist.specialistdirectory.domain.specialist.models.dtos.*;
import com.specialist.specialistdirectory.domain.specialist.models.enums.SpecialistState;
import com.specialist.specialistdirectory.domain.specialist.models.enums.SpecialistStatus;
import com.specialist.specialistdirectory.domain.specialist.models.filters.ExtendedSpecialistFilter;
import com.specialist.specialistdirectory.domain.specialist.models.filters.SpecialistFilter;
import com.specialist.specialistdirectory.domain.specialist.models.filters.SystemSpecialistFilter;
import com.specialist.specialistdirectory.domain.specialist.repositories.SpecialistRepository;
import com.specialist.specialistdirectory.domain.specialist.repositories.SpecificationRepository;
import com.specialist.specialistdirectory.domain.type.models.TypeEntity;
import com.specialist.specialistdirectory.domain.type.models.dtos.TypeCreateDto;
import com.specialist.specialistdirectory.domain.type.models.dtos.TypeResponseDto;
import com.specialist.specialistdirectory.domain.type.services.TypeConstants;
import com.specialist.specialistdirectory.domain.type.services.TypeService;
import com.specialist.specialistdirectory.exceptions.NullSpecialistStatusException;
import com.specialist.specialistdirectory.exceptions.SpecialistNotFoundByIdException;
import com.specialist.utils.pagination.PageDataHolder;
import com.specialist.utils.pagination.PageRequest;
import com.specialist.utils.pagination.PageResponse;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDateTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UnifiedSpecialistServiceUnitTests {

    @Mock
    private SpecialistRepository repository;
    @Mock
    private SpecificationRepository<SpecialistEntity> specificationRepository;
    @Mock
    private SpecialistCountService countService;
    @Mock
    private SpecialistMapper mapper;
    @Mock
    private SpecialistCacheService cacheService;
    @Mock
    private TypeService typeService;

    @InjectMocks
    private UnifiedSpecialistService service;

    private static final Long OTHER_TYPE_ID = 999L;

    @BeforeAll
    static void setUp() {
        TypeConstants.OTHER_TYPE_ID = OTHER_TYPE_ID;
    }

    @Test
    @DisplayName("UT: save() with regular type should save and cache")
    void save_regularType_shouldSaveAndCache() {
        SpecialistCreateDto dto = new SpecialistCreateDto("first", null, "last", 1L, null, 5, Collections.emptyList(), null, "city", "12345", Collections.emptyList(), null);
        dto.setCreatorId(UUID.randomUUID());
        
        SpecialistEntity entity = new SpecialistEntity();
        entity.setId(UUID.randomUUID());
        SpecialistResponseDto responseDto = mock(SpecialistResponseDto.class);

        when(mapper.toEntity(dto)).thenReturn(entity);
        when(typeService.getReferenceById(1L)).thenReturn(new TypeEntity());
        when(repository.save(entity)).thenReturn(entity);
        when(mapper.toResponseDto(entity)).thenReturn(responseDto);

        SpecialistResponseDto result = service.save(dto);

        assertEquals(responseDto, result);
        verify(repository).save(entity);
        verify(cacheService).putShortInfo(eq(entity.getId()), any(ShortSpecialistInfo.class));
        verify(typeService, never()).suggest(any());
    }

    @Test
    @DisplayName("UT: save() with other type should suggest type and save")
    void save_otherType_shouldSuggestAndSave() {
        SpecialistCreateDto dto = new SpecialistCreateDto("first", null, "last", TypeConstants.OTHER_TYPE_ID, "Another", 5, Collections.emptyList(), null, "city", "12345", Collections.emptyList(), null);
        dto.setCreatorId(UUID.randomUUID());

        SpecialistEntity entity = new SpecialistEntity();
        entity.setId(UUID.randomUUID());
        SpecialistResponseDto responseDto = mock(SpecialistResponseDto.class);

        when(mapper.toEntity(dto)).thenReturn(entity);
        when(typeService.suggest(any(TypeCreateDto.class))).thenReturn(100L);
        when(typeService.getReferenceById(TypeConstants.OTHER_TYPE_ID)).thenReturn(new TypeEntity());
        when(repository.save(entity)).thenReturn(entity);
        when(mapper.toResponseDto(entity)).thenReturn(responseDto);

        service.save(dto);

        assertEquals(100L, entity.getSuggestedTypeId());
        verify(typeService).suggest(any(TypeCreateDto.class));
        verify(repository).save(entity);
    }

    @Test
    @DisplayName("UT: update() when type changed to other should suggest type")
    void update_typeChangedToOther_shouldSuggest() {
        SpecialistUpdateDto dto = new SpecialistUpdateDto("first", null, "last", TypeConstants.OTHER_TYPE_ID, "NewType", 5, Collections.emptyList(), null, "city", "12345", Collections.emptyList(), null);
        dto.setId(UUID.randomUUID());
        dto.setAccountId(UUID.randomUUID());

        SpecialistEntity entity = new SpecialistEntity();
        entity.setType(new TypeEntity());
        entity.getType().setId(1L); // Was regular type

        when(repository.findWithTypeById(dto.getId())).thenReturn(Optional.of(entity));
        when(typeService.suggest(any(TypeCreateDto.class))).thenReturn(200L);
        when(typeService.getReferenceById(TypeConstants.OTHER_TYPE_ID)).thenReturn(new TypeEntity());
        when(repository.save(entity)).thenReturn(entity);
        when(mapper.toResponseDto(entity)).thenReturn(mock(SpecialistResponseDto.class));

        service.update(dto);

        assertEquals(200L, entity.getSuggestedTypeId());
        verify(typeService).suggest(any(TypeCreateDto.class));
    }

    @Test
    @DisplayName("UT: update() when type changed from other should clear suggested type")
    void update_typeChangedFromOther_shouldClearSuggested() {
        SpecialistUpdateDto dto = new SpecialistUpdateDto("first", null, "last", 1L, null, 5, Collections.emptyList(), null, "city", "12345", Collections.emptyList(), null);
        dto.setId(UUID.randomUUID());

        SpecialistEntity entity = new SpecialistEntity();
        entity.setType(new TypeEntity());
        entity.getType().setId(TypeConstants.OTHER_TYPE_ID); // Was other type
        entity.setSuggestedTypeId(100L);

        when(repository.findWithTypeById(dto.getId())).thenReturn(Optional.of(entity));
        when(typeService.getReferenceById(1L)).thenReturn(new TypeEntity());
        when(repository.save(entity)).thenReturn(entity);
        when(mapper.toResponseDto(entity)).thenReturn(mock(SpecialistResponseDto.class));

        service.update(dto);

        assertNull(entity.getSuggestedTypeId());
        verify(typeService, never()).suggest(any());
    }

    @Test
    @DisplayName("UT: update() when other type title changed should suggest new type")
    void update_otherTypeTitleChanged_shouldSuggest() {
        SpecialistUpdateDto dto = new SpecialistUpdateDto("first", null, "last", TypeConstants.OTHER_TYPE_ID, "NewTitle", 5, Collections.emptyList(), null, "city", "12345", Collections.emptyList(), null);
        dto.setId(UUID.randomUUID());
        dto.setAccountId(UUID.randomUUID());

        SpecialistEntity entity = new SpecialistEntity();
        entity.setType(new TypeEntity());
        entity.getType().setId(TypeConstants.OTHER_TYPE_ID);
        entity.setSuggestedTypeId(100L);

        TypeResponseDto typeDto = new TypeResponseDto(100L, UUID.randomUUID(), "OldTitle", false, LocalDateTime.now(), LocalDateTime.now());

        when(repository.findWithTypeById(dto.getId())).thenReturn(Optional.of(entity));
        when(typeService.findSuggestedById(100L)).thenReturn(typeDto);
        when(typeService.suggest(any(TypeCreateDto.class))).thenReturn(200L);
        when(repository.save(entity)).thenReturn(entity);
        when(mapper.toResponseDto(entity)).thenReturn(mock(SpecialistResponseDto.class));

        service.update(dto);

        assertEquals(200L, entity.getSuggestedTypeId());
        verify(typeService).suggest(any(TypeCreateDto.class));
    }

    @Test
    @DisplayName("UT: update() when other type title NOT changed should NOT suggest")
    void update_otherTypeTitleNotChanged_shouldNotSuggest() {
        SpecialistUpdateDto dto = new SpecialistUpdateDto("first", null, "last", TypeConstants.OTHER_TYPE_ID, "OldTitle", 5, Collections.emptyList(), null, "city", "12345", Collections.emptyList(), null);
        dto.setId(UUID.randomUUID());

        SpecialistEntity entity = new SpecialistEntity();
        entity.setType(new TypeEntity());
        entity.getType().setId(TypeConstants.OTHER_TYPE_ID);
        entity.setSuggestedTypeId(100L);

        TypeResponseDto typeDto = new TypeResponseDto(100L, UUID.randomUUID(), "OldTitle", false, LocalDateTime.now(), LocalDateTime.now());

        when(repository.findWithTypeById(dto.getId())).thenReturn(Optional.of(entity));
        when(typeService.findSuggestedById(100L)).thenReturn(typeDto);
        when(repository.save(entity)).thenReturn(entity);
        when(mapper.toResponseDto(entity)).thenReturn(mock(SpecialistResponseDto.class));

        service.update(dto);

        verify(typeService, never()).suggest(any());
    }

    @Test
    @DisplayName("UT: update() when regular type changed to regular should update type")
    void update_regularToRegular_shouldUpdateType() {
        SpecialistUpdateDto dto = new SpecialistUpdateDto("first", null, "last", 2L, null, 5, Collections.emptyList(), null, "city", "12345", Collections.emptyList(), null);
        dto.setId(UUID.randomUUID());

        SpecialistEntity entity = new SpecialistEntity();
        entity.setType(new TypeEntity());
        entity.getType().setId(1L);

        when(repository.findWithTypeById(dto.getId())).thenReturn(Optional.of(entity));
        when(typeService.getReferenceById(2L)).thenReturn(new TypeEntity());
        when(repository.save(entity)).thenReturn(entity);
        when(mapper.toResponseDto(entity)).thenReturn(mock(SpecialistResponseDto.class));

        service.update(dto);

        verify(typeService).getReferenceById(2L);
        verify(typeService, never()).suggest(any());
    }

    @Test
    @DisplayName("UT: update() when type not changed should do nothing with type")
    void update_typeNotChanged_shouldDoNothingWithType() {
        SpecialistUpdateDto dto = new SpecialistUpdateDto("first", null, "last", 1L, null, 5, Collections.emptyList(), null, "city", "12345", Collections.emptyList(), null);
        dto.setId(UUID.randomUUID());

        SpecialistEntity entity = new SpecialistEntity();
        entity.setType(new TypeEntity());
        entity.getType().setId(1L);

        when(repository.findWithTypeById(dto.getId())).thenReturn(Optional.of(entity));
        when(repository.save(entity)).thenReturn(entity);
        when(mapper.toResponseDto(entity)).thenReturn(mock(SpecialistResponseDto.class));

        service.update(dto);

        verify(typeService, never()).getReferenceById(any());
        verify(typeService, never()).suggest(any());
    }

    @Test
    @DisplayName("UT: update() when not found should throw SpecialistNotFoundByIdException")
    void update_notFound_shouldThrow() {
        SpecialistUpdateDto dto = new SpecialistUpdateDto("first", null, "last", 1L, null, 5, Collections.emptyList(), null, "city", "12345", Collections.emptyList(), null);
        dto.setId(UUID.randomUUID());

        when(repository.findWithTypeById(dto.getId())).thenReturn(Optional.empty());

        assertThrows(SpecialistNotFoundByIdException.class, () -> service.update(dto));
    }

    @Test
    @DisplayName("UT: updateAllByTypeIdPair() should call repository")
    void updateAllByTypeIdPair_shouldCallRepository() {
        service.updateAllByTypeIdPair(1L, 2L);
        verify(repository).updateAllByTypeTitle(1L, 2L);
    }

    @Test
    @DisplayName("UT: getShortInfoById() should return info")
    void getShortInfoById_shouldReturnInfo() {
        UUID id = UUID.randomUUID();
        ShortSpecialistInfo info = new ShortSpecialistInfo(id, UUID.randomUUID(), UUID.randomUUID(), SpecialistStatus.APPROVED);
        when(repository.findShortInfoById(id)).thenReturn(Optional.of(info));

        ShortSpecialistInfo result = service.getShortInfoById(id);

        assertEquals(info, result);
    }

    @Test
    @DisplayName("UT: getShortInfoById() when not found should throw SpecialistNotFoundByIdException")
    void getShortInfoById_notFound_shouldThrow() {
        UUID id = UUID.randomUUID();
        when(repository.findShortInfoById(id)).thenReturn(Optional.empty());
        assertThrows(SpecialistNotFoundByIdException.class, () -> service.getShortInfoById(id));
    }

    @Test
    @DisplayName("UT: findById() should return dto with another type title")
    void findById_shouldReturnDtoWithAnotherType() {
        UUID id = UUID.randomUUID();
        SpecialistEntity entity = new SpecialistEntity();
        entity.setSuggestedTypeId(100L);
        SpecialistResponseDto dto = mock(SpecialistResponseDto.class);

        when(repository.findWithTypeById(id)).thenReturn(Optional.of(entity));
        when(mapper.toResponseDto(entity)).thenReturn(dto);
        when(typeService.findSuggestedById(100L)).thenReturn(new TypeResponseDto(100L, UUID.randomUUID(), "Title", false, LocalDateTime.now(), LocalDateTime.now()));

        SpecialistResponseDto result = service.findById(id);

        verify(dto).setAnotherType("Title");
    }

    @Test
    @DisplayName("UT: findById() when suggested type null should return dto without another type")
    void findById_suggestedTypeNull_shouldReturnDto() {
        UUID id = UUID.randomUUID();
        SpecialistEntity entity = new SpecialistEntity();
        entity.setSuggestedTypeId(null);
        SpecialistResponseDto dto = mock(SpecialistResponseDto.class);

        when(repository.findWithTypeById(id)).thenReturn(Optional.of(entity));
        when(mapper.toResponseDto(entity)).thenReturn(dto);

        service.findById(id);

        verify(dto, never()).setAnotherType(any());
    }

    @Test
    @DisplayName("UT: findById() when not found should throw SpecialistNotFoundByIdException")
    void findById_notFound_shouldThrow() {
        UUID id = UUID.randomUUID();
        when(repository.findWithTypeById(id)).thenReturn(Optional.empty());
        assertThrows(SpecialistNotFoundByIdException.class, () -> service.findById(id));
    }

    @Test
    @DisplayName("UT: findByCreatorIdAndId() when creator matches should return another type")
    void findByCreatorIdAndId_creatorMatches_shouldReturnAnotherType() {
        UUID id = UUID.randomUUID();
        UUID creatorId = UUID.randomUUID();
        SpecialistEntity entity = new SpecialistEntity();
        entity.setSuggestedTypeId(100L);
        entity.setCreatorId(creatorId);
        SpecialistResponseDto dto = mock(SpecialistResponseDto.class);

        when(repository.findWithTypeById(id)).thenReturn(Optional.of(entity));
        when(mapper.toResponseDto(entity)).thenReturn(dto);
        when(typeService.findSuggestedById(100L)).thenReturn(new TypeResponseDto(100L, UUID.randomUUID(), "Title", false, LocalDateTime.now(), LocalDateTime.now()));

        SpecialistResponseDto result = service.findByCreatorIdAndId(creatorId, id);

        verify(dto).setAnotherType("Title");
    }

    @Test
    @DisplayName("UT: findByCreatorIdAndId() when creator mismatch should return null another type")
    void findByCreatorIdAndId_creatorMismatch_shouldReturnNullAnotherType() {
        UUID id = UUID.randomUUID();
        UUID creatorId = UUID.randomUUID();
        SpecialistEntity entity = new SpecialistEntity();
        entity.setSuggestedTypeId(100L);
        entity.setCreatorId(UUID.randomUUID()); // Different creator
        SpecialistResponseDto dto = mock(SpecialistResponseDto.class);

        when(repository.findWithTypeById(id)).thenReturn(Optional.of(entity));
        when(mapper.toResponseDto(entity)).thenReturn(dto);

        SpecialistResponseDto result = service.findByCreatorIdAndId(creatorId, id);

        verify(dto).setAnotherType(null);
    }

    @Test
    @DisplayName("UT: findByCreatorIdAndId() when not found should throw SpecialistNotFoundByIdException")
    void findByCreatorIdAndId_notFound_shouldThrow() {
        UUID id = UUID.randomUUID();
        UUID creatorId = UUID.randomUUID();
        when(repository.findWithTypeById(id)).thenReturn(Optional.empty());
        assertThrows(SpecialistNotFoundByIdException.class, () -> service.findByCreatorIdAndId(creatorId, id));
    }

    @Test
    @DisplayName("UT: findByIdAndState() should return dto with another type")
    void findByIdAndState_shouldReturnDto() {
        UUID id = UUID.randomUUID();
        SpecialistEntity entity = new SpecialistEntity();
        entity.setSuggestedTypeId(100L);
        SpecialistResponseDto dto = mock(SpecialistResponseDto.class);

        when(repository.findWithTypeByIdAndState(id, SpecialistState.MANAGED)).thenReturn(Optional.of(entity));
        when(mapper.toResponseDto(entity)).thenReturn(dto);
        when(typeService.findSuggestedById(100L)).thenReturn(new TypeResponseDto(100L, UUID.randomUUID(), "Title", false, LocalDateTime.now(), LocalDateTime.now()));

        SpecialistResponseDto result = service.findByIdAndState(id, SpecialistState.MANAGED);

        verify(dto).setAnotherType("Title");
    }

    @Test
    @DisplayName("UT: findByIdAndState() when suggested type null should return dto")
    void findByIdAndState_suggestedTypeNull_shouldReturnDto() {
        UUID id = UUID.randomUUID();
        SpecialistEntity entity = new SpecialistEntity();
        entity.setSuggestedTypeId(null);
        SpecialistResponseDto dto = mock(SpecialistResponseDto.class);

        when(repository.findWithTypeByIdAndState(id, SpecialistState.MANAGED)).thenReturn(Optional.of(entity));
        when(mapper.toResponseDto(entity)).thenReturn(dto);

        service.findByIdAndState(id, SpecialistState.MANAGED);

        verify(dto, never()).setAnotherType(any());
    }

    @Test
    @DisplayName("UT: findByIdAndState() when not found should throw SpecialistNotFoundByIdException")
    void findByIdAndState_notFound_shouldThrow() {
        UUID id = UUID.randomUUID();
        when(repository.findWithTypeByIdAndState(id, SpecialistState.MANAGED)).thenReturn(Optional.empty());
        assertThrows(SpecialistNotFoundByIdException.class, () -> service.findByIdAndState(id, SpecialistState.MANAGED));
    }

    @Test
    @DisplayName("UT: findEntityById() should return entity")
    void findEntityById_shouldReturnEntity() {
        UUID id = UUID.randomUUID();
        SpecialistEntity entity = new SpecialistEntity();
        when(repository.findById(id)).thenReturn(Optional.of(entity));

        SpecialistEntity result = service.findEntityById(id);

        assertEquals(entity, result);
    }

    @Test
    @DisplayName("UT: findEntityById() when not found should throw SpecialistNotFoundByIdException")
    void findEntityById_notFound_shouldThrow() {
        UUID id = UUID.randomUUID();
        when(repository.findById(id)).thenReturn(Optional.empty());
        assertThrows(SpecialistNotFoundByIdException.class, () -> service.findEntityById(id));
    }

    @Test
    @DisplayName("UT: getReferenceById() should return reference")
    void getReferenceById_shouldReturnReference() {
        UUID id = UUID.randomUUID();
        SpecialistEntity entity = new SpecialistEntity();
        when(repository.getReferenceById(id)).thenReturn(entity);

        SpecialistEntity result = service.getReferenceById(id);

        assertEquals(entity, result);
    }

    @Test
    @DisplayName("UT: findAll(PageDataHolder) should return page")
    void findAll_pageDataHolder_shouldReturnPage() {
        PageDataHolder pageData = new PageRequest(0, 10, true);
        Slice<SpecialistEntity> slice = new PageImpl<>(Collections.emptyList());
        when(specificationRepository.findAll(any(Specification.class), any(Pageable.class))).thenReturn(slice);
        when(countService.countAll()).thenReturn(100L);
        when(mapper.toResponseDtoList(anyList())).thenReturn(Collections.emptyList());

        PageResponse<SpecialistResponseDto> result = service.findAll(pageData);

        assertNotNull(result);
        assertEquals(10, result.totalPages());
    }

    @Test
    @DisplayName("UT: findAll(SystemSpecialistFilter) when status null should throw exception")
    void findAll_statusNull_shouldThrow() {
        SystemSpecialistFilter filter = new SystemSpecialistFilter(mock(PageDataHolder.class), null, null);
        assertThrows(NullSpecialistStatusException.class, () -> service.findAll(filter));
    }

    @Test
    @DisplayName("UT: findAll(SystemSpecialistFilter) should return list")
    void findAll_systemFilter_shouldReturnList() {
        SystemSpecialistFilter filter = new SystemSpecialistFilter(new PageRequest(0, 10, true), SpecialistStatus.APPROVED, SpecialistState.MANAGED);
        Page<SpecialistEntity> slice = new PageImpl<>(Collections.emptyList());

        when(repository.findAll(any(Specification.class), any(Pageable.class))).thenReturn(slice);
        when(mapper.toResponseDtoList(anyList())).thenReturn(Collections.emptyList());

        List<SpecialistResponseDto> result = service.findAll(filter);

        assertNotNull(result);
    }

    @Test
    @DisplayName("UT: findAllByFilter() should return page")
    void findAllByFilter_shouldReturnPage() {
        SpecialistFilter filter = new SpecialistFilter("city", null, null, null, null, null, true, 0, 10);
        Slice<SpecialistEntity> slice = new PageImpl<>(Collections.emptyList());
        when(specificationRepository.findAll(any(Specification.class), any(Pageable.class))).thenReturn(slice);
        when(countService.countByFilter(filter)).thenReturn(100L);
        when(mapper.toResponseDtoList(anyList())).thenReturn(Collections.emptyList());

        PageResponse<SpecialistResponseDto> result = service.findAllByFilter(filter);

        assertNotNull(result);
        assertEquals(10, result.totalPages());
    }

    @Test
    @DisplayName("UT: findAllByCreatorIdAndFilter() should return page")
    void findAllByCreatorIdAndFilter_shouldReturnPage() {
        UUID creatorId = UUID.randomUUID();
        ExtendedSpecialistFilter filter = new ExtendedSpecialistFilter("city", null, null, null, null, null, null, null, null, true, 0, 10);
        Slice<SpecialistEntity> slice = new PageImpl<>(Collections.emptyList());
        when(specificationRepository.findAll(any(Specification.class), any(Pageable.class))).thenReturn(slice);
        when(countService.countByCreatorIdAndFilter(creatorId, filter)).thenReturn(100L);
        when(mapper.toResponseDtoList(anyList())).thenReturn(Collections.emptyList());

        PageResponse<SpecialistResponseDto> result = service.findAllByCreatorIdAndFilter(creatorId, filter);

        assertNotNull(result);
        assertEquals(10, result.totalPages());
    }

    @Test
    @DisplayName("UT: findAllByFilterAndIdIn() should return page")
    void findAllByFilterAndIdIn_shouldReturnPage() {
        ExtendedSpecialistFilter filter = new ExtendedSpecialistFilter("city", null, null, null, null, null, null, null, null, true, 0, 10);
        List<UUID> ids = List.of(UUID.randomUUID());
        Page<SpecialistEntity> page = new PageImpl<>(Collections.emptyList());
        when(repository.findAll(any(Specification.class), any(Pageable.class))).thenReturn(page);

        Page<SpecialistEntity> result = service.findAllByFilterAndIdIn(filter, ids);

        assertEquals(page, result);
    }

    @Test
    @DisplayName("UT: deleteById() should delete and evict cache")
    void deleteById_shouldDeleteAndEvict() {
        UUID id = UUID.randomUUID();
        UUID creatorId = UUID.randomUUID();
        ShortSpecialistInfo info = new ShortSpecialistInfo(id, creatorId, UUID.randomUUID(), SpecialistStatus.APPROVED);

        when(cacheService.getShortInfo(id)).thenReturn(info);

        service.deleteById(id);

        verify(repository).deleteById(id);
        verify(cacheService).evictCacheAfterDelete(id, creatorId);
    }

    @Test
    @DisplayName("UT: deleteById() when cache miss should find creator id")
    void deleteById_cacheMiss_shouldFindCreatorId() {
        UUID id = UUID.randomUUID();
        UUID creatorId = UUID.randomUUID();
        ShortSpecialistInfo info = new ShortSpecialistInfo(null, null, null, null); // Empty info

        when(cacheService.getShortInfo(id)).thenReturn(info);
        when(repository.findCreatorIdById(id)).thenReturn(Optional.of(creatorId));

        service.deleteById(id);

        verify(repository).deleteById(id);
        verify(cacheService).evictCacheAfterDelete(id, creatorId);
    }

    @Test
    @DisplayName("UT: deleteById() when cache miss and not found should throw SpecialistNotFoundByIdException")
    void deleteById_cacheMiss_notFound_shouldThrow() {
        UUID id = UUID.randomUUID();
        ShortSpecialistInfo info = new ShortSpecialistInfo(null, null, null, null); // Empty info

        when(cacheService.getShortInfo(id)).thenReturn(info);
        when(repository.findCreatorIdById(id)).thenReturn(Optional.empty());

        assertThrows(SpecialistNotFoundByIdException.class, () -> service.deleteById(id));
    }

    @Test
    @DisplayName("UT: deleteByOwnerId() should find info, delete and evict")
    void deleteByOwnerId_shouldDeleteAndEvict() {
        UUID ownerId = UUID.randomUUID();
        UUID id = UUID.randomUUID();
        UUID creatorId = UUID.randomUUID();
        ShortSpecialistInfo info = new ShortSpecialistInfo(id, creatorId, ownerId, SpecialistStatus.APPROVED);

        when(repository.findShortInfoByOwnerId(ownerId)).thenReturn(Optional.of(info));

        service.deleteByOwnerId(ownerId);

        verify(repository).deleteByOwnerId(ownerId);
        verify(cacheService).evictCacheAfterDelete(id, creatorId);
    }

    @Test
    @DisplayName("UT: deleteByOwnerId() when not found should throw SpecialistNotFoundByIdException")
    void deleteByOwnerId_notFound_shouldThrow() {
        UUID ownerId = UUID.randomUUID();
        when(repository.findShortInfoByOwnerId(ownerId)).thenReturn(Optional.empty());
        assertThrows(SpecialistNotFoundByIdException.class, () -> service.deleteByOwnerId(ownerId));
    }

    @Test
    @DisplayName("UT: deleteAllByOwnerIds() should delete and evict all")
    void deleteAllByOwnerIds_shouldDeleteAndEvictAll() {
        Set<UUID> ownerIds = Set.of(UUID.randomUUID());
        UUID id = UUID.randomUUID();
        UUID creatorId = UUID.randomUUID();
        ShortSpecialistInfo info = new ShortSpecialistInfo(id, creatorId, UUID.randomUUID(), SpecialistStatus.APPROVED);

        when(repository.findAllShortInfoByOwnerIdIn(ownerIds)).thenReturn(List.of(info));

        service.deleteAllByOwnerIds(ownerIds);

        verify(repository).deleteAllByOwnerIdIn(ownerIds);
        verify(cacheService).evictCacheAfterDelete(id, creatorId);
    }

    @Test
    @DisplayName("UT: toResponseDto() should map to bookmark dto")
    void toResponseDto_shouldMap() {
        SpecialistEntity entity = new SpecialistEntity();
        BookmarkSpecialistResponseDto dto = mock(BookmarkSpecialistResponseDto.class);
        when(mapper.toBookmarkResponseDto(entity)).thenReturn(dto);

        BookmarkSpecialistResponseDto result = service.toResponseDto(entity);

        assertEquals(dto, result);
    }
}
