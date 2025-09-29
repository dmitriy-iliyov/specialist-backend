package com.specialist.specialistdirectory.domain.bookmark.services;

import com.specialist.specialistdirectory.domain.bookmark.models.BookmarkCreateDto;
import com.specialist.specialistdirectory.domain.bookmark.models.BookmarkResponseDto;

public interface BookmarkPersistService {
    BookmarkResponseDto save(BookmarkCreateDto dto);

    void saveAfterSpecialistCreate(BookmarkCreateDto dto);
}
