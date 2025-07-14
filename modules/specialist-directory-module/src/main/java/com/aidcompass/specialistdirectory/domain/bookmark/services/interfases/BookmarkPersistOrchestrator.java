package com.aidcompass.specialistdirectory.domain.bookmark.services.interfases;

import com.aidcompass.specialistdirectory.domain.specialist.models.dtos.SpecialistResponseDto;

import java.util.UUID;

public interface BookmarkPersistOrchestrator {
    SpecialistResponseDto save(UUID ownerId, UUID specialistId);

    void saveAfterSpecialistCreate(UUID ownerId, UUID specialistId);
}
