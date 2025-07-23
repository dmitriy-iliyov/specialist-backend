package com.aidcompass.specialistdirectory.domain.bookmark.services.interfases;

import com.aidcompass.specialistdirectory.domain.bookmark.models.BookmarkCreateDto;
import com.aidcompass.specialistdirectory.domain.bookmark.models.BookmarkResponseDto;
import com.aidcompass.specialistdirectory.domain.specialist.models.dtos.SpecialistResponseDto;
import com.aidcompass.specialistdirectory.domain.specialist.models.filters.ExtendedSpecialistFilter;
import com.aidcompass.specialistdirectory.utils.pagination.PageRequest;
import com.aidcompass.specialistdirectory.utils.pagination.PageResponse;

import java.util.UUID;

public interface BookmarkService {
    BookmarkResponseDto save(BookmarkCreateDto dto);

    boolean existsByOwnerIdAndSpecialistId(UUID ownerId, UUID specialistId);

    void deleteById(UUID ownerId, UUID specialistId);

    PageResponse<BookmarkResponseDto> findAllByOwnerId(UUID ownerId, PageRequest page);

    PageResponse<BookmarkResponseDto> findAllByOwnerIdAndFilter(UUID ownerId, ExtendedSpecialistFilter filter);

    void deleteAllByOwnerId(UUID ownerId);
}
