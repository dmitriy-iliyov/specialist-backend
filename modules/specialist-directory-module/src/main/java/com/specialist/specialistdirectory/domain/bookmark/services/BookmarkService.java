package com.specialist.specialistdirectory.domain.bookmark.services;

import com.specialist.specialistdirectory.domain.bookmark.models.BookmarkCreateDto;
import com.specialist.specialistdirectory.domain.bookmark.models.BookmarkResponseDto;
import com.specialist.specialistdirectory.domain.specialist.models.filters.ExtendedSpecialistFilter;
import com.specialist.utils.pagination.PageRequest;
import com.specialist.utils.pagination.PageResponse;

import java.util.UUID;

public interface BookmarkService {
    BookmarkResponseDto save(BookmarkCreateDto dto);

    boolean existsByOwnerIdAndSpecialistId(UUID ownerId, UUID specialistId);

    void deleteById(UUID ownerId, UUID specialistId);

    PageResponse<BookmarkResponseDto> findAllByOwnerId(UUID ownerId, PageRequest page);

    PageResponse<BookmarkResponseDto> findAllByOwnerIdAndFilter(UUID ownerId, ExtendedSpecialistFilter filter);

    void deleteAllByOwnerId(UUID ownerId);
}
