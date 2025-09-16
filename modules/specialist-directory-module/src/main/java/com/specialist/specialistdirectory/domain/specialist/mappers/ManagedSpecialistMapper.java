package com.specialist.specialistdirectory.domain.specialist.mappers;

import com.specialist.contracts.specialistdirectory.dto.ManagedSpecialistResponseDto;
import com.specialist.specialistdirectory.domain.specialist.models.dtos.SpecialistResponseDto;

public interface ManagedSpecialistMapper {
    ManagedSpecialistResponseDto toManagedDto(SpecialistResponseDto dto);
}
