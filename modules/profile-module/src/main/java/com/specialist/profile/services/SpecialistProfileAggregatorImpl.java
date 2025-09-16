package com.specialist.profile.services;

import com.specialist.contracts.specialistdirectory.SystemSelfSpecialistService;
import com.specialist.contracts.specialistdirectory.dto.ManagedSpecialistResponseDto;
import com.specialist.profile.mappers.SpecialistProfileMapper;
import com.specialist.profile.models.dtos.PrivateSpecialistResponseDto;
import com.specialist.profile.models.dtos.PublicSpecialistResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class SpecialistProfileAggregatorImpl implements SpecialistProfileAggregator {

    private final SpecialistProfileService service;
    private final SystemSelfSpecialistService systemSelfSpecialistService;
    private final SpecialistProfileMapper mapper;

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
