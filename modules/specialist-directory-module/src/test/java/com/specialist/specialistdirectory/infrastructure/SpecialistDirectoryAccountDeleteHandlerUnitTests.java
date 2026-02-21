package com.specialist.specialistdirectory.infrastructure;

import com.specialist.contracts.auth.DeferAccountDeleteEvent;
import com.specialist.specialistdirectory.domain.bookmark.services.BookmarkService;
import com.specialist.specialistdirectory.domain.specialist.services.SpecialistService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Set;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class SpecialistDirectoryAccountDeleteHandlerUnitTests {

    @Mock
    private SpecialistService specialistService;

    @Mock
    private BookmarkService bookmarkService;

    @InjectMocks
    private SpecialistDirectoryAccountDeleteHandler handler;

    @Test
    @DisplayName("UT: handle() with specialist role should delete specialist data")
    void handle_specialistRole_shouldDeleteSpecialist() {
        UUID accountId = UUID.randomUUID();
        DeferAccountDeleteEvent event = new DeferAccountDeleteEvent(accountId, "ROLE_SPECIALIST");
        List<DeferAccountDeleteEvent> events = List.of(event);

        handler.handle(events);

        verify(bookmarkService).deleteAllByOwnerIds(Set.of(accountId));
        verify(specialistService).deleteAllByOwnerIds(Set.of(accountId));
    }

    @Test
    @DisplayName("UT: handle() with user role should NOT delete specialist data")
    void handle_userRole_shouldNotDeleteSpecialist() {
        UUID accountId = UUID.randomUUID();
        DeferAccountDeleteEvent event = new DeferAccountDeleteEvent(accountId, "ROLE_USER");
        List<DeferAccountDeleteEvent> events = List.of(event);

        handler.handle(events);

        verify(bookmarkService).deleteAllByOwnerIds(Set.of(accountId));
        verify(specialistService, never()).deleteAllByOwnerIds(any());
    }
}
