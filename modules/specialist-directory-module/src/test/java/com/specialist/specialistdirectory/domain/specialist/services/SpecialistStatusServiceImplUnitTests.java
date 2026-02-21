package com.specialist.specialistdirectory.domain.specialist.services;

import com.specialist.specialistdirectory.domain.specialist.models.SpecialistEntity;
import com.specialist.specialistdirectory.domain.specialist.models.SpecialistInfoEntity;
import com.specialist.specialistdirectory.domain.specialist.models.enums.ApproverType;
import com.specialist.specialistdirectory.domain.specialist.models.enums.SpecialistStatus;
import com.specialist.specialistdirectory.domain.specialist.repositories.SpecialistRepository;
import com.specialist.specialistdirectory.exceptions.UnableSpecialistApproveException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SpecialistStatusServiceImplUnitTests {

    @Mock
    private SpecialistRepository repository;

    @InjectMocks
    private SpecialistStatusServiceImpl service;

    @Test
    @DisplayName("UT: approve() when status is UNAPPROVED should approve")
    void approve_whenUnapproved_shouldApprove() {
        UUID id = UUID.randomUUID();
        UUID approverId = UUID.randomUUID();
        ApproverType approverType = ApproverType.ADMIN;
        
        SpecialistEntity specialistEntity = new SpecialistEntity();
        specialistEntity.setStatus(SpecialistStatus.UNAPPROVED);
        specialistEntity.setInfo(new SpecialistInfoEntity());

        when(repository.findWithInfoById(id)).thenReturn(Optional.of(specialistEntity));

        service.approve(id, approverId, approverType);

        assertEquals(SpecialistStatus.APPROVED, specialistEntity.getStatus());
        assertEquals(approverId, specialistEntity.getInfo().getApproverId());
        assertEquals(approverType, specialistEntity.getInfo().getApproverType());
        verify(repository).save(specialistEntity);
    }

    @Test
    @DisplayName("UT: approve() when status is not UNAPPROVED should throw exception")
    void approve_whenNotUnapproved_shouldThrow() {
        UUID id = UUID.randomUUID();
        SpecialistEntity specialistEntity = new SpecialistEntity();
        specialistEntity.setStatus(SpecialistStatus.APPROVED);

        when(repository.findWithInfoById(id)).thenReturn(Optional.of(specialistEntity));

        assertThrows(UnableSpecialistApproveException.class, () -> service.approve(id, UUID.randomUUID(), ApproverType.ADMIN));
    }
}
