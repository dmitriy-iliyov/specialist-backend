package com.specialist.specialistdirectory.domain.bookmark.services;

import com.specialist.specialistdirectory.domain.bookmark.models.BookmarkCreateDto;
import com.specialist.specialistdirectory.domain.bookmark.models.BookmarkResponseDto;
import com.specialist.specialistdirectory.exceptions.AlreadyExistsException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class BookmarkPersistOrchestratorImpl implements BookmarkPersistOrchestrator {

    private final BookmarkService service;

    @Transactional
    @Override
    public BookmarkResponseDto save(BookmarkCreateDto dto) {
        if (service.existsByOwnerIdAndSpecialistId(dto.getOwnerId(), dto.getSpecialistId())) {
            throw new AlreadyExistsException();
        }
        return service.save(dto);
    }

    @Transactional
    @Override
    public void saveAfterSpecialistCreate(BookmarkCreateDto dto) {
        if (service.existsByOwnerIdAndSpecialistId(dto.getOwnerId(), dto.getSpecialistId())) {
            return;
        }
        service.save(dto);
    }
}
