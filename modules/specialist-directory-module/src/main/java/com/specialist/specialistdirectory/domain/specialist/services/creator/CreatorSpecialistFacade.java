package com.specialist.specialistdirectory.domain.specialist.services.creator;

import com.specialist.specialistdirectory.domain.specialist.models.dtos.SpecialistResponseDto;
import com.specialist.specialistdirectory.domain.specialist.models.filters.ExtendedSpecialistFilter;
import com.specialist.utils.pagination.PageResponse;

import java.util.UUID;

public interface CreatorSpecialistFacade {
    SpecialistResponseDto findById(UUID creatorId, UUID id);

    PageResponse<SpecialistResponseDto> findAllByFilter(UUID creatorId, ExtendedSpecialistFilter filter);

    long count(UUID creatorId);
}
