package com.specialist.specialistdirectory.domain.specialist.services.creator;

import com.specialist.contracts.profile.ProfileType;
import com.specialist.specialistdirectory.domain.bookmark.models.BookmarkCreateDto;
import com.specialist.specialistdirectory.domain.bookmark.services.BookmarkPersistService;
import com.specialist.specialistdirectory.domain.specialist.models.dtos.*;
import com.specialist.specialistdirectory.domain.specialist.models.enums.CreatorType;
import com.specialist.specialistdirectory.domain.specialist.models.enums.SpecialistState;
import com.specialist.specialistdirectory.domain.specialist.models.enums.SpecialistStatus;
import com.specialist.specialistdirectory.domain.specialist.services.SpecialistCacheService;
import com.specialist.specialistdirectory.domain.specialist.services.SpecialistManagementStrategy;
import com.specialist.specialistdirectory.domain.specialist.services.SpecialistService;
import com.specialist.specialistdirectory.exceptions.OwnershipException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class CreatorSpecialistManagementStrategy implements SpecialistManagementStrategy {

    private final SpecialistService specialistService;
    private final SpecialistCacheService cacheService;
    private final BookmarkPersistService bookmarkPersistService;

    @Override
    public ProfileType getType() {
        return ProfileType.USER;
    }

    @Transactional
    @Override
    public SpecialistResponseDto save(SpecialistCreateRequest request) {
        SpecialistCreateDto dto = request.dto();
        dto.setCreatorId(request.creatorId());
        dto.setCreatorType(CreatorType.USER);
        dto.setStatus(SpecialistStatus.UNAPPROVED);
        dto.setState(SpecialistState.DEFAULT);
        SpecialistResponseDto responseDto = specialistService.save(dto);
        cacheService.evictCacheAfterSave(request.creatorId());
        bookmarkPersistService.saveAfterSpecialistCreate(new BookmarkCreateDto(responseDto.getOwnerId(), responseDto.getId()));
        // outbox
        return responseDto;
    }

    @Override
    public SpecialistResponseDto update(SpecialistUpdateDto dto) {
        validate(dto.getAccountId(), dto.getId());
        // outbox ??
        return specialistService.update(dto);
    }

    @Transactional
    @Override
    public void delete(UUID accountId, UUID id) {
        validate(accountId, id);
        specialistService.deleteById(id);
        // cache evict in service
    }

    private void validate(UUID accountId, UUID id) {
        ShortSpecialistInfo info = specialistService.getShortInfoById(id);
        if (!info.ownerId().equals(accountId)) {
            throw new OwnershipException();
        }
    }
}