package com.specialist.specialistdirectory.domain.specialist.services.specialist;

import com.specialist.specialistdirectory.domain.specialist.models.dtos.SpecialistCreateDto;
import com.specialist.specialistdirectory.domain.specialist.models.dtos.SpecialistResponseDto;
import com.specialist.specialistdirectory.domain.specialist.models.dtos.SpecialistUpdateDto;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.util.UUID;

public interface SelfSpecialistOrchestrator {
    SpecialistResponseDto save(UUID creatorId, SpecialistCreateDto dto, HttpServletRequest request, HttpServletResponse response);

    SpecialistResponseDto update(SpecialistUpdateDto dto);

    void delete(UUID accountId, UUID id);
}
