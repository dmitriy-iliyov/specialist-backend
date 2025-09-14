package com.specialist.user.services;

import com.specialist.contracts.specialistdirectory.ManagedSpecialistResponseDto;
import com.specialist.contracts.specialistdirectory.SystemSelfSpecialistService;
import com.specialist.user.mappers.SpecialistMapper;
import com.specialist.user.models.dtos.PrivateSpecialistResponseDto;
import com.specialist.user.models.dtos.PublicSpecialistResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class SpecialistReadOrchestratorImpl implements SpecialistReadOrchestrator {

    private final SpecialistService service;
    private final SystemSelfSpecialistService systemSelfSpecialistService;
    private final SpecialistMapper mapper;

    @Transactional(readOnly = true)
    @Override
    public PrivateSpecialistResponseDto findPrivateById(UUID id) {
        PrivateSpecialistResponseDto dto = service.findPrivateById(id);
        if (dto.hasCard()) {
            ManagedSpecialistResponseDto managedDto = systemSelfSpecialistService.findById(id);
            return mapper.aggregate(dto, managedDto);
        }
        return dto;
    }

    @Transactional(readOnly = true)
    @Override
    public PublicSpecialistResponseDto findPublicById(UUID id) {
        PublicSpecialistResponseDto dto = service.findPublicById(id);
        if (dto.hasCard()) {
            ManagedSpecialistResponseDto managedDto = systemSelfSpecialistService.findById(id);
            return mapper.aggregate(dto, managedDto);
        }
        return dto;    }
}
