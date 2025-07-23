package com.aidcompass.specialistdirectory.domain.bookmark.services.interfases;

import com.aidcompass.specialistdirectory.domain.bookmark.models.BookmarkCreateDto;
import com.aidcompass.specialistdirectory.domain.specialist.models.dtos.SpecialistResponseDto;

import java.util.UUID;

public interface BookmarkPersistOrchestrator {
    SpecialistResponseDto save(BookmarkCreateDto dto);

    void saveAfterSpecialistCreate(BookmarkCreateDto dto);
}
