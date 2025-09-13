package com.specialist.specialistdirectory.domain.specialist.services;

import com.specialist.contracts.user.UserType;
import com.specialist.specialistdirectory.domain.specialist.models.dtos.SpecialistResponseDto;
import com.specialist.specialistdirectory.domain.specialist.models.dtos.SpecialistUpdateDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class SpecialistManagementOrchestratorImpl implements SpecialistManagementOrchestrator {

    private final CreatorSpecialistOrchestrator creatorOrchestrator;
    private final ManagedSpecialistOrchestrator managedOrchestrator;

    @Override
    public SpecialistResponseDto update(SpecialistUpdateDto dto, UserType type) {
        return switch (type) {
            case USER -> creatorOrchestrator.update(dto);
            case SPECIALIST -> managedOrchestrator.update(dto);
        };
    }

    @Override
    public void delete(UUID accountId, UUID id, UserType type) {
        switch (type) {
            case USER -> creatorOrchestrator.delete(accountId, id);
            case SPECIALIST -> managedOrchestrator.delete(accountId, id);
        };
    }
}
