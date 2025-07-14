package com.aidcompass.specialistdirectory.domain.bookmark.services.interfases;

import com.aidcompass.specialistdirectory.domain.specialist.models.dtos.SpecialistResponseDto;
import com.aidcompass.specialistdirectory.domain.specialist.models.filters.ExtendedSpecialistFilter;
import com.aidcompass.specialistdirectory.utils.pagination.PageRequest;
import com.aidcompass.specialistdirectory.utils.pagination.PageResponse;

import java.util.UUID;

public interface BookmarkService {
    SpecialistResponseDto save(UUID ownerId, UUID specialistId);

    boolean existsByOwnerIdAndSpecialistId(UUID ownerId, UUID specialistId);

    void deleteById(UUID ownerId, UUID specialistId);

    PageResponse<SpecialistResponseDto> findAllByOwnerId(UUID ownerId, PageRequest page);

    PageResponse<SpecialistResponseDto> findAllByOwnerIdAndFilter(UUID ownerId, ExtendedSpecialistFilter filter);

    void deleteAllByOwnerId(UUID ownerId);
}
