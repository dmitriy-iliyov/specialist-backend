package com.specialist.specialistdirectory.ut.domain.bookmark;

import com.specialist.contracts.auth.PrincipalDetails;
import com.specialist.specialistdirectory.domain.bookmark.BookmarkController;
import com.specialist.specialistdirectory.domain.bookmark.models.BookmarkCreateDto;
import com.specialist.specialistdirectory.domain.bookmark.models.BookmarkResponseDto;
import com.specialist.specialistdirectory.domain.bookmark.services.BookmarkCountService;
import com.specialist.specialistdirectory.domain.bookmark.services.BookmarkPersistService;
import com.specialist.specialistdirectory.domain.bookmark.services.BookmarkService;
import com.specialist.specialistdirectory.domain.specialist.models.filters.ExtendedSpecialistFilter;
import com.specialist.utils.pagination.PageResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BookmarkControllerUnitTests {

    @Mock
    private BookmarkService service;

    @Mock
    private BookmarkPersistService persistService;

    @Mock
    private BookmarkCountService countService;

    @InjectMocks
    private BookmarkController controller;

    @Test
    @DisplayName("UT: create() should set ownerId and call persistService")
    void create_shouldSetOwnerIdAndCallPersistService() {
        UUID ownerId = UUID.randomUUID();
        PrincipalDetails principal = mock(PrincipalDetails.class);
        when(principal.getAccountId()).thenReturn(ownerId);
        
        BookmarkCreateDto dto = new BookmarkCreateDto(null, UUID.randomUUID());
        BookmarkResponseDto responseDto = mock(BookmarkResponseDto.class);

        when(persistService.save(dto)).thenReturn(responseDto);

        ResponseEntity<?> result = controller.create(principal, dto);

        assertEquals(HttpStatus.CREATED, result.getStatusCode());
        assertEquals(responseDto, result.getBody());
        assertEquals(ownerId, dto.getOwnerId());
        verify(persistService).save(dto);
    }

    @Test
    @DisplayName("UT: delete() should call service")
    void delete_shouldCallService() {
        UUID ownerId = UUID.randomUUID();
        UUID id = UUID.randomUUID();
        PrincipalDetails principal = mock(PrincipalDetails.class);
        when(principal.getAccountId()).thenReturn(ownerId);

        ResponseEntity<?> result = controller.delete(principal, id.toString());

        assertEquals(HttpStatus.NO_CONTENT, result.getStatusCode());
        verify(service).deleteById(ownerId, id);
    }

    @Test
    @DisplayName("UT: getAll() should call service")
    void getAll_shouldCallService() {
        UUID ownerId = UUID.randomUUID();
        PrincipalDetails principal = mock(PrincipalDetails.class);
        when(principal.getAccountId()).thenReturn(ownerId);
        
        ExtendedSpecialistFilter filter = new ExtendedSpecialistFilter(null, null, null, null, null, null, null, null, null, true, 0, 10);

        PageResponse<BookmarkResponseDto> pageResponse = new PageResponse<>(Collections.emptyList(), 0);

        when(service.findAllByOwnerIdAndFilter(ownerId, filter)).thenReturn(pageResponse);

        ResponseEntity<?> result = controller.getAll(principal, filter);

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(pageResponse, result.getBody());
        verify(service).findAllByOwnerIdAndFilter(ownerId, filter);
    }

    @Test
    @DisplayName("UT: count() should call countService")
    void count_shouldCallCountService() {
        UUID ownerId = UUID.randomUUID();
        PrincipalDetails principal = mock(PrincipalDetails.class);
        when(principal.getAccountId()).thenReturn(ownerId);

        when(countService.countByOwnerId(ownerId)).thenReturn(5L);

        ResponseEntity<?> result = controller.count(principal);

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(5L, result.getBody());
        verify(countService).countByOwnerId(ownerId);
    }
}
