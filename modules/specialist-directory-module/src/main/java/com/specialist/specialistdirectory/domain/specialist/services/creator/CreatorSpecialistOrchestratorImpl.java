package com.specialist.specialistdirectory.domain.specialist.services.creator;

import com.specialist.specialistdirectory.domain.bookmark.models.BookmarkCreateDto;
import com.specialist.specialistdirectory.domain.bookmark.services.BookmarkPersistService;
import com.specialist.specialistdirectory.domain.specialist.models.dtos.ShortSpecialistInfo;
import com.specialist.specialistdirectory.domain.specialist.models.dtos.SpecialistCreateDto;
import com.specialist.specialistdirectory.domain.specialist.models.dtos.SpecialistResponseDto;
import com.specialist.specialistdirectory.domain.specialist.models.dtos.SpecialistUpdateDto;
import com.specialist.specialistdirectory.domain.specialist.models.enums.CreatorType;
import com.specialist.specialistdirectory.domain.specialist.models.enums.SpecialistStatus;
import com.specialist.specialistdirectory.domain.specialist.services.SpecialistService;
import com.specialist.specialistdirectory.exceptions.ManagedSpecialistException;
import com.specialist.specialistdirectory.exceptions.OwnershipException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class CreatorSpecialistOrchestratorImpl implements CreatorSpecialistOrchestrator {

    private final SpecialistService specialistService;
    private final BookmarkPersistService bookmarkPersistService;

    @Transactional
    @Override
    public SpecialistResponseDto save(UUID creatorId, SpecialistCreateDto dto) {
        dto.setCreatorId(creatorId);
        dto.setCreatorType(CreatorType.USER);
        dto.setStatus(SpecialistStatus.UNAPPROVED);
        SpecialistResponseDto responseDto = specialistService.save(dto);
        bookmarkPersistService.saveAfterSpecialistCreate(new BookmarkCreateDto(responseDto.getOwnerId(), responseDto.getId()));
        return responseDto;
    }

    @Override
    public SpecialistResponseDto update(SpecialistUpdateDto dto) {
        validate(dto.getAccountId(), dto.getId());
        return specialistService.update(dto);
    }

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