package com.aidcompass.specialistdirectory.domain.specialist.services;

import com.aidcompass.specialistdirectory.domain.specialist.models.SpecialistEntity;
import com.aidcompass.specialistdirectory.domain.specialist.models.dtos.SpecialistResponseDto;
import com.aidcompass.specialistdirectory.domain.specialist.models.filters.ExtendedSpecialistFilter;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.UUID;

public interface SystemSpecialistService {

    SpecialistEntity findEntityById(UUID id);

    SpecialistResponseDto toResponseDto(SpecialistEntity entity);

    SpecialistEntity getReferenceById(UUID id);

    Page<SpecialistEntity> findAllByFilterAndIdIn(ExtendedSpecialistFilter filter, List<UUID> ids);
}
