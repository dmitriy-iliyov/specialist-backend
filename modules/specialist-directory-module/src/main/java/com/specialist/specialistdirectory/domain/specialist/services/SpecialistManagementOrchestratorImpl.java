package com.specialist.specialistdirectory.domain.specialist.services;

import com.specialist.contracts.profile.ProfileType;
import com.specialist.specialistdirectory.domain.specialist.models.dtos.SpecialistCreateDto;
import com.specialist.specialistdirectory.domain.specialist.models.dtos.SpecialistResponseDto;
import com.specialist.specialistdirectory.domain.specialist.models.dtos.SpecialistUpdateDto;
import com.specialist.specialistdirectory.domain.specialist.services.creator.CreatorSpecialistOrchestrator;
import com.specialist.specialistdirectory.domain.specialist.services.specialist.SelfSpecialistOrchestrator;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class SpecialistManagementOrchestratorImpl implements SpecialistManagementOrchestrator {

    private final CreatorSpecialistOrchestrator creatorOrchestrator;
    private final SelfSpecialistOrchestrator selfOrchestrator;

    @Override
    public SpecialistResponseDto save(UUID creatorId, ProfileType type, SpecialistCreateDto dto,
                                      HttpServletRequest request, HttpServletResponse response) {
        return switch (type) {
            case USER -> creatorOrchestrator.save(creatorId, dto);
            case SPECIALIST -> selfOrchestrator.save(creatorId, dto, request, response);
        };
    }

    @Override
    public SpecialistResponseDto update(SpecialistUpdateDto dto, ProfileType type) {
        return switch (type) {
            case USER -> creatorOrchestrator.update(dto);
            case SPECIALIST -> selfOrchestrator.update(dto);
        };
    }

    @Override
    public void delete(UUID accountId, UUID id, ProfileType type) {
        switch (type) {
            case USER -> creatorOrchestrator.delete(accountId, id);
            case SPECIALIST -> selfOrchestrator.delete(accountId, id);
        };
    }
}
