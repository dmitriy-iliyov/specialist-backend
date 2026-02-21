package com.specialist.specialistdirectory.infrastructure;

import com.specialist.contracts.auth.DeferAccountDeleteEvent;
import com.specialist.contracts.auth.DeferAccountDeleteHandler;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class SpecialistDirectoryEventListenerUnitTests {

    @Mock
    private DeferAccountDeleteHandler handler;

    @InjectMocks
    private SpecialistDirectoryEventListener listener;

    @Test
    @DisplayName("UT: listen() should delegate to handler")
    void listen_shouldDelegate() {
        List<DeferAccountDeleteEvent> events = Collections.emptyList();
        listener.listen(events);
        verify(handler).handle(events);
    }
}
