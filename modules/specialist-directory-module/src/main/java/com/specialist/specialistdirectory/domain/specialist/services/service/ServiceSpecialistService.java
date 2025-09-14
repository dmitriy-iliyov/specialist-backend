package com.specialist.specialistdirectory.domain.specialist.services.service;

import com.specialist.specialistdirectory.domain.specialist.models.dtos.SpecialistCreateDto;
import com.specialist.specialistdirectory.domain.specialist.models.dtos.SpecialistResponseDto;

import java.util.UUID;

public interface ServiceSpecialistService {
    SpecialistResponseDto save(UUID creatorId, SpecialistCreateDto dto);
}
