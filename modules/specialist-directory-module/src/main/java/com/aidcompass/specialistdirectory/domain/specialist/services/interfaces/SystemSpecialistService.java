package com.aidcompass.specialistdirectory.domain.specialist.services.interfaces;

import com.aidcompass.specialistdirectory.domain.specialist.models.SpecialistEntity;
import com.aidcompass.specialistdirectory.domain.specialist.models.dtos.SpecialistResponseDto;
import com.aidcompass.specialistdirectory.domain.specialist.models.filters.ExtendedSpecialistFilter;
import com.aidcompass.specialistdirectory.utils.pagination.PageResponse;

import java.util.List;
import java.util.UUID;

public interface SystemSpecialistService {

    SpecialistEntity findEntityById(UUID id);

    SpecialistResponseDto toResponseDto(SpecialistEntity entity);

    SpecialistEntity getReferenceById(UUID id);

    PageResponse<SpecialistResponseDto> findAllByFilterAndIdIn(ExtendedSpecialistFilter filter, List<UUID> ids);

    List<SpecialistResponseDto> toResponseDtoList(List<SpecialistEntity> entityList);
}
