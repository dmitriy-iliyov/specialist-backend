package com.specialist.specialistdirectory.domain.specialist.services;

import com.specialist.specialistdirectory.domain.specialist.models.dtos.SpecialistCreateDto;
import com.specialist.specialistdirectory.domain.specialist.models.dtos.SpecialistResponseDto;
import com.specialist.specialistdirectory.domain.specialist.models.enums.CreatorType;
import com.specialist.specialistdirectory.exceptions.NullSpecialistPersistServiceException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class SpecialistPersistOrchestratorImpl implements SpecialistPersistOrchestrator {

    private final Map<CreatorType, SpecialistPersistService> serviceMap;

    public SpecialistPersistOrchestratorImpl(List<SpecialistPersistService> serviceList) {
        this.serviceMap = serviceList.stream()
                .collect(Collectors.toMap(SpecialistPersistService::getType, Function.identity()));
    }

    @Override
    public SpecialistResponseDto save(UUID creatorId, CreatorType creatorType, SpecialistCreateDto dto) {
        dto.setCreatorId(creatorId);
        dto.setCreatorType(creatorType);
        SpecialistPersistService persistService = serviceMap.get(creatorType);
        if (persistService == null) {
            throw new NullSpecialistPersistServiceException();
        }
        return persistService.save(dto);
    }
}