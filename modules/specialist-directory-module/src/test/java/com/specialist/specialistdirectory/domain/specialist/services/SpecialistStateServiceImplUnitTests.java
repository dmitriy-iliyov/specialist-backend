package com.specialist.specialistdirectory.domain.specialist.services;

import com.specialist.specialistdirectory.domain.specialist.models.enums.SpecialistState;
import com.specialist.specialistdirectory.domain.specialist.repositories.SpecialistRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class SpecialistStateServiceImplUnitTests {

    @Mock
    private SpecialistRepository repository;

    @InjectMocks
    private SpecialistStateServiceImpl service;

    @Test
    @DisplayName("UT: manage() should update state and owner")
    void manage_shouldUpdateStateAndOwner() {
        UUID id = UUID.randomUUID();
        UUID ownerId = UUID.randomUUID();

        service.manage(id, ownerId);

        verify(repository).updateStateAndOwnerIdById(id, ownerId, SpecialistState.MANAGED);
    }

    @Test
    @DisplayName("UT: recall() should update state")
    void recall_shouldUpdateState() {
        UUID id = UUID.randomUUID();

        service.recall(id);

        verify(repository).updateStateById(id, SpecialistState.RECALLED);
    }
}
