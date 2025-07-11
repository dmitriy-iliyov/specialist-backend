package com.aidcompass.specialistdirectory.domain.bookmark;

import com.aidcompass.specialistdirectory.domain.specialist.models.dtos.SpecialistResponseDto;
import com.aidcompass.specialistdirectory.domain.specialist.models.filters.ExtendedSpecialistFilter;
import com.aidcompass.specialistdirectory.utils.PageRequest;
import com.aidcompass.specialistdirectory.utils.PageResponse;

import java.util.UUID;

public interface BookmarkService {
    SpecialistResponseDto save(UUID ownerId, UUID specialistId);

    void deleteBySpecialistId(UUID ownerId, UUID specialistId);

    PageResponse<SpecialistResponseDto> findAllByOwnerId(UUID ownerId, PageRequest page);

    PageResponse<SpecialistResponseDto> findAllByOwnerIdAndFilter(UUID ownerId, ExtendedSpecialistFilter filter);
}
