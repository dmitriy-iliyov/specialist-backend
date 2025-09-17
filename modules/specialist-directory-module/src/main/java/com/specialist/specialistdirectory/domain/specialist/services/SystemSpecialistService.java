package com.specialist.specialistdirectory.domain.specialist.services;

import com.specialist.specialistdirectory.domain.specialist.models.SpecialistEntity;
import com.specialist.specialistdirectory.domain.specialist.models.dtos.BookmarkSpecialistResponseDto;
import com.specialist.specialistdirectory.domain.specialist.models.dtos.SpecialistResponseDto;
import com.specialist.specialistdirectory.domain.specialist.models.enums.SpecialistStatus;
import com.specialist.specialistdirectory.domain.specialist.models.filters.ExtendedSpecialistFilter;
import com.specialist.specialistdirectory.domain.specialist.models.filters.SystemSpecialistFilter;
import com.specialist.utils.pagination.PageResponse;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.UUID;

public interface SystemSpecialistService {

    SpecialistResponseDto findByIdAndStatus(UUID id, SpecialistStatus status);

    SpecialistEntity findEntityById(UUID id);

    BookmarkSpecialistResponseDto toResponseDto(SpecialistEntity entity);

    SpecialistEntity getReferenceById(UUID id);

    Page<SpecialistEntity> findAllByFilterAndIdIn(ExtendedSpecialistFilter filter, List<UUID> ids);

    List<SpecialistResponseDto> findAll(SystemSpecialistFilter filter);
}
