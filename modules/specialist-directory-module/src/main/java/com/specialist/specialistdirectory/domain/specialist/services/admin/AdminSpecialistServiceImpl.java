package com.specialist.specialistdirectory.domain.specialist.services;

import com.specialist.specialistdirectory.domain.specialist.models.dtos.SpecialistCreateDto;
import com.specialist.specialistdirectory.domain.specialist.models.dtos.SpecialistResponseDto;
import com.specialist.specialistdirectory.domain.specialist.models.enums.CreatorType;
import com.specialist.specialistdirectory.domain.specialist.models.enums.SpecialistStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdminSpecialistServiceImpl implements AdminSpecialistService, SpecialistPersistService {

    private final SpecialistService service;

    @Override
    public SpecialistResponseDto save(SpecialistCreateDto dto) {
        dto.setStatus(SpecialistStatus.APPROVED);
        return service.save(dto);
    }

    @Override
    public CreatorType getType() {
        return CreatorType.ADMIN;
    }
}
