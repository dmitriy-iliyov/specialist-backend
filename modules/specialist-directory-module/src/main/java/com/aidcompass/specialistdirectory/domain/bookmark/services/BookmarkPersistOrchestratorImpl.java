package com.aidcompass.specialistdirectory.domain.bookmark.services;

import com.aidcompass.specialistdirectory.domain.bookmark.services.interfases.BookmarkPersistOrchestrator;
import com.aidcompass.specialistdirectory.domain.bookmark.services.interfases.BookmarkService;
import com.aidcompass.specialistdirectory.domain.specialist.models.dtos.SpecialistResponseDto;
import com.aidcompass.specialistdirectory.exceptions.AlreadyExistsException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class BookmarkPersistOrchestratorImpl implements BookmarkPersistOrchestrator {

    private final BookmarkService service;


    @Override
    public SpecialistResponseDto save(UUID ownerId, UUID specialistId) {
        if (service.existsByOwnerIdAndSpecialistId(ownerId, specialistId)) {
            throw new AlreadyExistsException();
        }
        return service.save(ownerId, specialistId);
    }

    @Override
    public void saveAfterSpecialistCreate(UUID ownerId, UUID specialistId) {
        if (service.existsByOwnerIdAndSpecialistId(ownerId, specialistId)) {
            return;
        }
        service.save(ownerId, specialistId);
    }
}
