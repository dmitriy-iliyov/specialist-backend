package com.specialist.specialistdirectory.domain.specialist.services;

import com.specialist.contracts.profile.ProfileType;
import com.specialist.specialistdirectory.domain.specialist.models.dtos.SpecialistCreateRequest;
import com.specialist.specialistdirectory.domain.specialist.models.dtos.SpecialistResponseDto;
import com.specialist.specialistdirectory.domain.specialist.models.dtos.SpecialistUpdateDto;
import com.specialist.specialistdirectory.exceptions.NullSpecialistStrategyManagementException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@Slf4j
public class SpecialistManagementOrchestratorImpl implements SpecialistManagementOrchestrator {

    private final Map<ProfileType, SpecialistManagementStrategy> strategyMap;

    public SpecialistManagementOrchestratorImpl(List<SpecialistManagementStrategy> strategies) {
        this.strategyMap = strategies.stream()
                .collect(Collectors.toMap(SpecialistManagementStrategy::getType, Function.identity()));
    }

    @Override
    public SpecialistResponseDto save(SpecialistCreateRequest request) {
        return resolveStrategy(request.profileType()).save(request);
    }

    @Override
    public SpecialistResponseDto update(SpecialistUpdateDto dto, ProfileType type) {
        return resolveStrategy(type).update(dto);
    }

    @Override
    public void delete(UUID accountId, UUID id, ProfileType type) {
        resolveStrategy(type).delete(accountId, id);
    }

    private SpecialistManagementStrategy resolveStrategy(ProfileType type) {
        SpecialistManagementStrategy strategy = strategyMap.get(type);
        if (strategy == null) {
            log.error("SpecialistManagementStrategy not found for type {}", type);
            throw new NullSpecialistStrategyManagementException();
        }
        return strategy;
    }
}
