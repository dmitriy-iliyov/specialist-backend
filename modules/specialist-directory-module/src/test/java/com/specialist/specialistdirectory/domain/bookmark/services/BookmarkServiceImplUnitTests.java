package com.specialist.specialistdirectory.domain.bookmark.services;

import com.specialist.specialistdirectory.domain.bookmark.BookmarkRepository;
import com.specialist.specialistdirectory.domain.bookmark.models.BookmarkCreateDto;
import com.specialist.specialistdirectory.domain.bookmark.models.BookmarkEntity;
import com.specialist.specialistdirectory.domain.bookmark.models.BookmarkIdPair;
import com.specialist.specialistdirectory.domain.bookmark.models.BookmarkResponseDto;
import com.specialist.specialistdirectory.domain.specialist.models.SpecialistEntity;
import com.specialist.specialistdirectory.domain.specialist.models.dtos.BookmarkSpecialistResponseDto;
import com.specialist.specialistdirectory.domain.specialist.models.filters.ExtendedSpecialistFilter;
import com.specialist.specialistdirectory.domain.specialist.services.SystemSpecialistService;
import com.specialist.utils.pagination.PageResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.util.List;
import java.util.Set;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BookmarkServiceImplUnitTests {

    @Mock
    private SystemSpecialistService specialistService;

    @Mock
    private BookmarkRepository bookmarkRepository;

    @Mock
    private BookmarkCountService bookmarkCountService;

    @Mock
    private CacheManager cacheManager;

    @InjectMocks
    private BookmarkServiceImpl service;

    @Test
    @DisplayName("UT: save() should find specialist, save bookmark and return dto")
    void save_shouldSave() {
        BookmarkCreateDto dto = new BookmarkCreateDto(UUID.randomUUID(), UUID.randomUUID());
        SpecialistEntity specialistEntity = new SpecialistEntity();
        BookmarkEntity bookmarkEntity = new BookmarkEntity(dto.getOwnerId(), specialistEntity);
        bookmarkEntity.setId(UUID.randomUUID());
        BookmarkSpecialistResponseDto specialistDto = mock(BookmarkSpecialistResponseDto.class);

        when(specialistService.findEntityById(dto.getSpecialistId())).thenReturn(specialistEntity);
        when(bookmarkRepository.save(any(BookmarkEntity.class))).thenReturn(bookmarkEntity);
        when(specialistService.toResponseDto(specialistEntity)).thenReturn(specialistDto);

        BookmarkResponseDto result = service.save(dto);

        assertEquals(bookmarkEntity.getId(), result.id());
        assertEquals(specialistDto, result.specialist());
        verify(bookmarkRepository).save(any(BookmarkEntity.class));
    }

    @Test
    @DisplayName("UT: existsByOwnerIdAndSpecialistId() should call repository")
    void existsByOwnerIdAndSpecialistId_shouldCallRepository() {
        UUID ownerId = UUID.randomUUID();
        UUID specialistId = UUID.randomUUID();
        when(bookmarkRepository.existsByOwnerIdAndSpecialistId(ownerId, specialistId)).thenReturn(true);

        assertTrue(service.existsByOwnerIdAndSpecialistId(ownerId, specialistId));
    }

    @Test
    @DisplayName("UT: deleteById() should delegate to repository")
    void deleteById_shouldDelegate() {
        UUID ownerId = UUID.randomUUID();
        UUID id = UUID.randomUUID();
        service.deleteById(ownerId, id);
        verify(bookmarkRepository).deleteById(id);
    }

    @Test
    @DisplayName("UT: findAllByOwnerIdAndFilter() should find ids, filter specialists and map")
    void findAllByOwnerIdAndFilter_shouldFindAndMap() {
        UUID ownerId = UUID.randomUUID();
        ExtendedSpecialistFilter filter = new ExtendedSpecialistFilter(null, null, null, null, null, null, null, null, null, true, 0, 10);
        UUID specialistId = UUID.randomUUID();
        UUID bookmarkId = UUID.randomUUID();
        BookmarkIdPair pair = new BookmarkIdPair(bookmarkId, specialistId);
        SpecialistEntity specialistEntity = new SpecialistEntity();
        specialistEntity.setId(specialistId);
        Page<SpecialistEntity> page = new PageImpl<>(List.of(specialistEntity));
        BookmarkSpecialistResponseDto specialistDto = mock(BookmarkSpecialistResponseDto.class);

        when(bookmarkCountService.findAllIdPairByOwnerId(ownerId)).thenReturn(List.of(pair));
        when(specialistService.findAllByFilterAndIdIn(eq(filter), anyList())).thenReturn(page);
        when(specialistService.toResponseDto(specialistEntity)).thenReturn(specialistDto);

        PageResponse<BookmarkResponseDto> result = service.findAllByOwnerIdAndFilter(ownerId, filter);

        assertEquals(1, result.data().size());
        assertEquals(bookmarkId, result.data().get(0).id());
        assertEquals(specialistDto, result.data().get(0).specialist());
    }

    @Test
    @DisplayName("UT: deleteAllByOwnerId() should delegate to repository")
    void deleteAllByOwnerId_shouldDelegate() {
        UUID ownerId = UUID.randomUUID();
        service.deleteAllByOwnerId(ownerId);
        verify(bookmarkRepository).deleteAllByOwnerId(ownerId);
    }

    @Test
    @DisplayName("UT: deleteAllByOwnerIds() should delete and evict caches")
    void deleteAllByOwnerIds_shouldDeleteAndEvict() {
        Set<UUID> ownerIds = Set.of(UUID.randomUUID());
        Cache totalCache = mock(Cache.class);
        Cache idPairsCache = mock(Cache.class);

        when(cacheManager.getCache("specialists:bookmarks:count:total")).thenReturn(totalCache);
        when(cacheManager.getCache("specialists:bookmarks:id_pairs")).thenReturn(idPairsCache);

        service.deleteAllByOwnerIds(ownerIds);

        verify(bookmarkRepository).deleteAllByOwnerIdIn(ownerIds);
        verify(totalCache).evict(any());
        verify(idPairsCache).evict(any());
    }

    @Test
    @DisplayName("UT: deleteAllByOwnerIds() when caches are null should not throw exception")
    void deleteAllByOwnerIds_nullCaches_shouldNotThrow() {
        Set<UUID> ownerIds = Set.of(UUID.randomUUID());

        when(cacheManager.getCache("specialists:bookmarks:count:total")).thenReturn(null);
        when(cacheManager.getCache("specialists:bookmarks:id_pairs")).thenReturn(null);

        service.deleteAllByOwnerIds(ownerIds);

        verify(bookmarkRepository).deleteAllByOwnerIdIn(ownerIds);
    }
}
