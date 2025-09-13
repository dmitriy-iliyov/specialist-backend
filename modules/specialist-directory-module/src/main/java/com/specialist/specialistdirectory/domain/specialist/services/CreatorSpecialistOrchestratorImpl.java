package com.specialist.specialistdirectory.domain.specialist.services;

import com.specialist.specialistdirectory.domain.bookmark.models.BookmarkCreateDto;
import com.specialist.specialistdirectory.domain.bookmark.services.BookmarkOrchestrator;
import com.specialist.specialistdirectory.domain.specialist.models.dtos.ShortSpecialistInfo;
import com.specialist.specialistdirectory.domain.specialist.models.dtos.SpecialistCreateDto;
import com.specialist.specialistdirectory.domain.specialist.models.dtos.SpecialistResponseDto;
import com.specialist.specialistdirectory.domain.specialist.models.dtos.SpecialistUpdateDto;
import com.specialist.specialistdirectory.domain.specialist.models.enums.SpecialistStatus;
import com.specialist.specialistdirectory.exceptions.ManagedSpecialistException;
import com.specialist.specialistdirectory.exceptions.OwnershipException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class CreatorSpecialistOrchestratorImpl implements CreatorSpecialistOrchestrator {

    private final SpecialistService specialistService;
    private final BookmarkOrchestrator bookmarkOrchestrator;

    @Transactional
    @Override
    public SpecialistResponseDto save(SpecialistCreateDto dto) {
        SpecialistResponseDto responseDto = specialistService.save(dto);
        bookmarkOrchestrator.saveAfterSpecialistCreate(new BookmarkCreateDto(responseDto.getCreatorId(), responseDto.getId()));
        return responseDto;
    }

    @Override
    public SpecialistResponseDto update(SpecialistUpdateDto dto) {
        this.validate(dto.getAccountId(), dto.getId());
        return specialistService.update(dto);
    }

    @Caching(evict = {
            @CacheEvict(value = "specialists:bookmarks:count:total", key = "#accountId"),
            @CacheEvict(value = "specialists:bookmarks:id_pairs", key = "#accountId")}
    )
    @Transactional
    @Override
    public void delete(UUID accountId, UUID id) {
        validate(accountId, id);
        specialistService.deleteById(id);
    }

    private void validate(UUID accountId, UUID id) {
        ShortSpecialistInfo info = specialistService.getShortInfoById(id);
        if (info.status().equals(SpecialistStatus.MANAGED)) {
            throw new ManagedSpecialistException();
        }
        if (!info.ownerId().equals(accountId)) {
            throw new OwnershipException();
        }
        // DISCUSS: should user have authority to update RECALLED spec?
//        if (info.status().equals(SpecialistStatus.RECALLED)) {
//            throw new RecalledSpecialistException();
//        }
    }
}