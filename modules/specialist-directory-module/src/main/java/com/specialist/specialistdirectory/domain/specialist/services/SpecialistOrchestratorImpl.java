package com.specialist.specialistdirectory.domain.specialist.services;

import com.specialist.specialistdirectory.domain.bookmark.models.BookmarkCreateDto;
import com.specialist.specialistdirectory.domain.bookmark.services.BookmarkOrchestrator;
import com.specialist.specialistdirectory.domain.review.models.enums.OperationType;
import com.specialist.specialistdirectory.domain.specialist.models.dtos.SpecialistCreateDto;
import com.specialist.specialistdirectory.domain.specialist.models.dtos.SpecialistResponseDto;
import com.specialist.specialistdirectory.domain.specialist.models.dtos.SpecialistUpdateDto;
import com.specialist.specialistdirectory.exceptions.OwnershipException;
import jakarta.persistence.OptimisticLockException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Caching;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class SpecialistOrchestratorImpl implements SpecialistOrchestrator {

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
        this.assertOwnership(dto.getCreatorId(), dto.getId());
        return specialistService.update(dto);
    }

    @Retryable(
            retryFor = {OptimisticLockException.class},
            maxAttempts = 4,
            backoff = @Backoff(delay = 50)
    )
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @Override
    public void updateRatingById(UUID id, long rating, OperationType operation) {
        specialistService.updateRatingById(id, rating, operation);
    }

    @Recover
    public void recover(OptimisticLockException e, UUID id, long rating, OperationType operation) {
        log.error("Error when reviewing specialist: id={}, date={}, time={}", id, LocalDate.now(), LocalTime.now());
    }

    @Caching(evict = {
            @CacheEvict(value = "specialists:bookmarks:count:total", key = "#creatorId"),
            @CacheEvict(value = "specialists:bookmarks:id_pairs", key = "#creatorId")}
    )
    @Transactional
    @Override
    public void delete(UUID creatorId, UUID id) {
        assertOwnership(creatorId, id);
        specialistService.deleteById(id);
    }

    private void assertOwnership(UUID creatorId, UUID id) {
        UUID realCreatorId = specialistService.getCreatorIdById(id);
        if (!realCreatorId.equals(creatorId)) {
            throw new OwnershipException();
        }
    }
}