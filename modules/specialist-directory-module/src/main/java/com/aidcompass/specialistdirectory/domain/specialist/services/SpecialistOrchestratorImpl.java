package com.aidcompass.specialistdirectory.domain.specialist.services;

import com.aidcompass.specialistdirectory.domain.bookmark.models.BookmarkCreateDto;
import com.aidcompass.specialistdirectory.domain.bookmark.services.BookmarkOrchestrator;
import com.aidcompass.specialistdirectory.domain.review.models.enums.RatingOperationType;
import com.aidcompass.specialistdirectory.domain.specialist.models.dtos.SpecialistCreateDto;
import com.aidcompass.specialistdirectory.domain.specialist.models.dtos.SpecialistResponseDto;
import com.aidcompass.specialistdirectory.domain.specialist.models.dtos.SpecialistUpdateDto;
import com.aidcompass.specialistdirectory.exceptions.OwnershipException;
import jakarta.persistence.OptimisticLockException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;
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

    @Override
    public void updateRatingById(UUID id, long rating, RatingOperationType operationType) {
        for (int i = 0; i < 2; i++) {
            try {
                specialistService.updateRatingById(id, rating, operationType);
                return;
            } catch (OptimisticLockException e) {
                log.warn("Optimizing look when reviewing specialist: id={}, date={}, time={}", id, LocalDate.now(), LocalTime.now());
                specialistService.updateRatingById(id, rating, operationType);
            }
            log.error("Error when reviewing specialist: id={}, date={}, time={}", id, LocalDate.now(), LocalTime.now());
        }
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