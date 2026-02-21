package com.specialist.specialistdirectory.domain.review.services;

import com.specialist.specialistdirectory.domain.review.models.dtos.*;
import com.specialist.specialistdirectory.domain.review.models.enums.NextOperationType;
import com.specialist.specialistdirectory.domain.review.models.enums.OperationType;
import com.specialist.specialistdirectory.domain.review.models.enums.ReviewAgeType;
import com.specialist.specialistdirectory.domain.review.validation.ReviewValidator;
import com.specialist.specialistdirectory.domain.specialist.models.SpecialistEntity;
import com.specialist.specialistdirectory.domain.specialist.services.SpecialistRatingService;
import com.specialist.specialistdirectory.domain.specialist.services.SystemSpecialistService;
import org.apache.commons.lang3.tuple.Pair;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DefaultReviewManagementFacadeUnitTests {

    @Mock
    private ReviewValidator validator;
    @Mock
    private ReviewService reviewService;
    @Mock
    private SystemSpecialistService specialistService;
    @Mock
    private SpecialistRatingService ratingService;
    @Mock
    private CreatorRatingUpdateService creatorRatingUpdateService;

    @InjectMocks
    private DefaultReviewManagementFacade facade;

    @Test
    @DisplayName("UT: save() should validate, save and update ratings")
    void save_shouldValidateAndSave() {
        ReviewCreateRequest request = new ReviewCreateRequest(UUID.randomUUID(), UUID.randomUUID(), "payload", null);
        ReviewPayload payload = new ReviewPayload("text", 5);
        SpecialistEntity specialist = new SpecialistEntity();
        ReviewResponseDto responseDto = mock(ReviewResponseDto.class);
        UUID specialistCreatorId = UUID.randomUUID();

        when(validator.validate(request.rawPayload())).thenReturn(payload);
        when(specialistService.getReferenceById(request.specialistId())).thenReturn(specialist);
        when(reviewService.save(eq(specialist), any(ReviewCreateDto.class))).thenReturn(responseDto);
        when(ratingService.updateRatingById(request.specialistId(), 5, OperationType.PERSIST)).thenReturn(specialistCreatorId);

        ReviewResponseDto result = facade.save(request);

        assertEquals(responseDto, result);
        verify(validator).validate(request.rawPayload());
        verify(reviewService).save(eq(specialist), any(ReviewCreateDto.class));
        verify(ratingService).updateRatingById(request.specialistId(), 5, OperationType.PERSIST);
        verify(creatorRatingUpdateService).updateByCreatorId(specialistCreatorId, 5, OperationType.PERSIST);
    }

    @Test
    @DisplayName("UT: update() when rating changed should update ratings")
    void update_ratingChanged_shouldUpdateRatings() {
        ReviewUpdateRequest request = new ReviewUpdateRequest(UUID.randomUUID(), UUID.randomUUID(), UUID.randomUUID(), "payload", null);
        ReviewPayload payload = new ReviewPayload("text", 4);
        ReviewResponseDto oldDto = new ReviewResponseDto(request.id(), request.creatorId(), "old", 5, null, LocalDateTime.now());
        ReviewResponseDto newDto = new ReviewResponseDto(request.id(), request.creatorId(), "text", 4, null, LocalDateTime.now());
        Pair<NextOperationType, Map<ReviewAgeType, ReviewResponseDto>> pair = Pair.of(NextOperationType.UPDATE, Map.of(ReviewAgeType.OLD, oldDto, ReviewAgeType.NEW, newDto));
        UUID specialistCreatorId = UUID.randomUUID();

        when(validator.validate(request.rawPayload())).thenReturn(payload);
        when(reviewService.update(any(ReviewUpdateDto.class))).thenReturn(pair);
        when(ratingService.updateRatingById(request.specialistId(), -1, OperationType.UPDATE)).thenReturn(specialistCreatorId);

        ReviewResponseDto result = facade.update(request);

        assertEquals(newDto, result);
        verify(ratingService).updateRatingById(request.specialistId(), -1, OperationType.UPDATE);
        verify(creatorRatingUpdateService).updateByCreatorId(specialistCreatorId, -1, OperationType.UPDATE);
    }

    @Test
    @DisplayName("UT: update() when rating not changed should not update ratings")
    void update_ratingNotChanged_shouldNotUpdateRatings() {
        ReviewUpdateRequest request = new ReviewUpdateRequest(UUID.randomUUID(), UUID.randomUUID(), UUID.randomUUID(), "payload", null);
        ReviewPayload payload = new ReviewPayload("text", 5);
        ReviewResponseDto oldDto = new ReviewResponseDto(request.id(), request.creatorId(), "old", 5, null, LocalDateTime.now());
        ReviewResponseDto newDto = new ReviewResponseDto(request.id(), request.creatorId(), "text", 5, null, LocalDateTime.now());
        Pair<NextOperationType, Map<ReviewAgeType, ReviewResponseDto>> pair = Pair.of(NextOperationType.SKIP_UPDATE, Map.of(ReviewAgeType.OLD, oldDto, ReviewAgeType.NEW, newDto));

        when(validator.validate(request.rawPayload())).thenReturn(payload);
        when(reviewService.update(any(ReviewUpdateDto.class))).thenReturn(pair);

        facade.update(request);

        verify(ratingService, never()).updateRatingById(any(), anyLong(), any());
        verify(creatorRatingUpdateService, never()).updateByCreatorId(any(), anyLong(), any());
    }

    @Test
    @DisplayName("UT: delete() should delete and update ratings")
    void delete_shouldDeleteAndUpdateRatings() {
        ReviewDeleteRequest request = new ReviewDeleteRequest(UUID.randomUUID(), UUID.randomUUID(), UUID.randomUUID());
        ReviewResponseDto dto = new ReviewResponseDto(request.id(), request.creatorId(), "text", 5, null, LocalDateTime.now());
        UUID specialistCreatorId = UUID.randomUUID();

        when(reviewService.deleteById(request.creatorId(), request.specialistId(), request.id())).thenReturn(dto);
        when(ratingService.updateRatingById(request.specialistId(), -5, OperationType.DELETE)).thenReturn(specialistCreatorId);

        facade.delete(request);

        verify(reviewService).deleteById(request.creatorId(), request.specialistId(), request.id());
        verify(ratingService).updateRatingById(request.specialistId(), -5, OperationType.DELETE);
        verify(creatorRatingUpdateService).updateByCreatorId(specialistCreatorId, -5, OperationType.DELETE);
    }
}
