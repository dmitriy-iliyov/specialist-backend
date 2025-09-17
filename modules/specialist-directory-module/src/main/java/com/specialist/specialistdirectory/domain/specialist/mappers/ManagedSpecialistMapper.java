package com.specialist.specialistdirectory.domain.specialist.mappers;

import com.specialist.contracts.specialistdirectory.dto.ManagedSpecialistResponseDto;
import com.specialist.specialistdirectory.domain.specialist.models.dtos.SpecialistResponseDto;

import java.util.List;

public interface ManagedSpecialistMapper {
    ManagedSpecialistResponseDto toManagedDto(SpecialistResponseDto dto);
    List<ManagedSpecialistResponseDto> toManagedDtoList(List<SpecialistResponseDto> dtoList);
}
