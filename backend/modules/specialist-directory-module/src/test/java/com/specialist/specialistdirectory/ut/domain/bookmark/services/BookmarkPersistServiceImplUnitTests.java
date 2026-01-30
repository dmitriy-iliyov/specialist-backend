package com.specialist.specialistdirectory.ut.domain.bookmark.services;

import com.specialist.specialistdirectory.domain.bookmark.models.BookmarkCreateDto;
import com.specialist.specialistdirectory.domain.bookmark.models.BookmarkResponseDto;
import com.specialist.specialistdirectory.domain.bookmark.services.BookmarkPersistServiceImpl;
import com.specialist.specialistdirectory.domain.bookmark.services.BookmarkService;
import com.specialist.specialistdirectory.exceptions.AlreadyExistsException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BookmarkPersistServiceImplUnitTests {

    @Mock
    private BookmarkService service;

    @InjectMocks
    private BookmarkPersistServiceImpl persistService;

    @Test
    @DisplayName("UT: save() when bookmark exists should throw AlreadyExistsException")
    void save_whenExists_shouldThrowException() {
        BookmarkCreateDto dto = new BookmarkCreateDto(UUID.randomUUID(), UUID.randomUUID());
        when(service.existsByOwnerIdAndSpecialistId(dto.getOwnerId(), dto.getSpecialistId())).thenReturn(true);

        assertThrows(AlreadyExistsException.class, () -> persistService.save(dto));
        verify(service, never()).save(any());
    }

    @Test
    @DisplayName("UT: save() when bookmark not exists should save and return dto")
    void save_whenNotExists_shouldSave() {
        BookmarkCreateDto dto = new BookmarkCreateDto(UUID.randomUUID(), UUID.randomUUID());
        BookmarkResponseDto responseDto = mock(BookmarkResponseDto.class);

        when(service.existsByOwnerIdAndSpecialistId(dto.getOwnerId(), dto.getSpecialistId())).thenReturn(false);
        when(service.save(dto)).thenReturn(responseDto);

        BookmarkResponseDto result = persistService.save(dto);

        assertEquals(responseDto, result);
        verify(service).save(dto);
    }

    @Test
    @DisplayName("UT: saveAfterSpecialistCreate() when bookmark exists should do nothing")
    void saveAfterSpecialistCreate_whenExists_shouldDoNothing() {
        BookmarkCreateDto dto = new BookmarkCreateDto(UUID.randomUUID(), UUID.randomUUID());
        when(service.existsByOwnerIdAndSpecialistId(dto.getOwnerId(), dto.getSpecialistId())).thenReturn(true);

        persistService.saveAfterSpecialistCreate(dto);

        verify(service, never()).save(any());
    }

    @Test
    @DisplayName("UT: saveAfterSpecialistCreate() when bookmark not exists should save")
    void saveAfterSpecialistCreate_whenNotExists_shouldSave() {
        BookmarkCreateDto dto = new BookmarkCreateDto(UUID.randomUUID(), UUID.randomUUID());
        when(service.existsByOwnerIdAndSpecialistId(dto.getOwnerId(), dto.getSpecialistId())).thenReturn(false);

        persistService.saveAfterSpecialistCreate(dto);

        verify(service).save(dto);
    }
}
