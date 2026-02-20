package com.specialist.specialistdirectory.domain.specialist.mappers;

import com.specialist.contracts.specialistdirectory.dto.ExternalManagedSpecialistResponseDto;
import com.specialist.specialistdirectory.domain.specialist.models.dtos.ManagedSpecialistResponseDto;
import com.specialist.specialistdirectory.domain.specialist.models.dtos.SpecialistResponseDto;

import java.util.List;

public interface ManagedSpecialistMapper {
    ManagedSpecialistResponseDto toManagedDto(SpecialistResponseDto dto, String avatarUrl);
    ExternalManagedSpecialistResponseDto toExternalManagedDto(SpecialistResponseDto dto);
    List<ExternalManagedSpecialistResponseDto> toExternalManagedDtoList(List<SpecialistResponseDto> dtoList);
}
