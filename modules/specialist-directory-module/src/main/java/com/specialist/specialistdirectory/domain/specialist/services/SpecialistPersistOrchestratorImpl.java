package com.specialist.specialistdirectory.domain.specialist.services;

import com.specialist.specialistdirectory.domain.specialist.models.dtos.SpecialistCreateDto;
import com.specialist.specialistdirectory.domain.specialist.models.dtos.SpecialistResponseDto;
import com.specialist.specialistdirectory.domain.specialist.models.enums.CreatorType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class SpecialistPersistOrchestratorImpl implements SpecialistPersistOrchestrator {

    private final SpecialistService service;
    private final SpecialistCreatorOrchestrator creatorOrchestrator;

    @Override
    public SpecialistResponseDto save(UUID creatorId, CreatorType creatorType, SpecialistCreateDto dto) {
        dto.setCreatorId(creatorId);
        dto.setCreatorType(creatorType);
        if (creatorType.equals(CreatorType.USER)) {
            dto.setApproved(false);
            return creatorOrchestrator.save(dto);
        } else {
            dto.setApproved(true);
            return service.save(dto);
        }
    }
}
