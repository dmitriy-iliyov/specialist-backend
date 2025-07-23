package com.aidcompass.specialistdirectory.domain.bookmark.services;

import com.aidcompass.specialistdirectory.domain.bookmark.models.BookmarkCreateDto;
import com.aidcompass.specialistdirectory.domain.bookmark.models.BookmarkResponseDto;
import com.aidcompass.specialistdirectory.domain.specialist.models.dtos.SpecialistResponseDto;

public interface BookmarkOrchestrator {
    BookmarkResponseDto save(BookmarkCreateDto dto);

    void saveAfterSpecialistCreate(BookmarkCreateDto dto);
}
