package com.aidcompass.specialistdirectory.domain.bookmark.services;

import com.aidcompass.specialistdirectory.domain.bookmark.models.BookmarkCreateDto;
import com.aidcompass.specialistdirectory.domain.bookmark.models.BookmarkResponseDto;
import com.aidcompass.specialistdirectory.domain.specialist.models.dtos.SpecialistResponseDto;
import com.aidcompass.specialistdirectory.exceptions.AlreadyExistsException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BookmarkOrchestratorImpl implements BookmarkOrchestrator {

    private final BookmarkService service;


    @Override
    public BookmarkResponseDto save(BookmarkCreateDto dto) {
        if (service.existsByOwnerIdAndSpecialistId(dto.getOwnerId(), dto.getSpecialistId())) {
            throw new AlreadyExistsException();
        }
        return service.save(dto);
    }

    @Override
    public void saveAfterSpecialistCreate(BookmarkCreateDto dto) {
        if (service.existsByOwnerIdAndSpecialistId(dto.getOwnerId(), dto.getSpecialistId())) {
            return;
        }
        service.save(dto);
    }
}
