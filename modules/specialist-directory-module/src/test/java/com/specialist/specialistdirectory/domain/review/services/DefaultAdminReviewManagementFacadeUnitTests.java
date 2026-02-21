package com.specialist.specialistdirectory.domain.review.services;

import com.specialist.contracts.profile.SystemProfileService;
import com.specialist.contracts.profile.dto.UnifiedProfileResponseDto;
import com.specialist.specialistdirectory.domain.review.models.dtos.ReviewAggregatedResponseDto;
import com.specialist.specialistdirectory.domain.review.models.dtos.ReviewResponseDto;
import com.specialist.specialistdirectory.domain.review.models.enums.OperationType;
import com.specialist.specialistdirectory.domain.review.models.enums.ReviewStatus;
import com.specialist.specialistdirectory.domain.review.models.filters.AdminReviewSort;
import com.specialist.specialistdirectory.domain.specialist.models.enums.ApproverType;
import com.specialist.specialistdirectory.domain.specialist.services.SpecialistRatingService;
import com.specialist.utils.pagination.PageResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DefaultAdminReviewManagementFacadeUnitTests {

    @Mock
    private ReviewService reviewService;
    @Mock
    private SpecialistRatingService ratingService;
    @Mock
    private CreatorRatingUpdateService creatorRatingUpdateService;
    @Mock
    private SystemProfileService profileService;

    @InjectMocks
    private DefaultAdminReviewManagementFacade facade;

    @Test
    @DisplayName("UT: getAll() should aggregate reviews with profiles")
    void getAll_shouldAggregate() {
        UUID specialistId = UUID.randomUUID();
        AdminReviewSort sort = new AdminReviewSort(0, 10, true, null, ReviewStatus.APPROVED);
        UUID creatorId = UUID.randomUUID();
        ReviewResponseDto reviewDto = new ReviewResponseDto(UUID.randomUUID(), creatorId, "text", 5, null, LocalDateTime.now());
        PageResponse<ReviewResponseDto> reviewsPage = new PageResponse<>(List.of(reviewDto), 1);
        UnifiedProfileResponseDto profileDto = mock(UnifiedProfileResponseDto.class);

        when(reviewService.findAllWithSortBySpecialistIdAndStatus(specialistId, ReviewStatus.APPROVED, sort)).thenReturn(reviewsPage);
        when(profileService.findAllByIdIn(any())).thenReturn(Map.of(creatorId, profileDto));

        PageResponse<ReviewAggregatedResponseDto> result = facade.getAll(specialistId, sort);

        assertEquals(1, result.data().size());
        assertEquals(reviewDto, result.data().get(0).review());
        assertEquals(profileDto, result.data().get(0).user());
    }

    @Test
    @DisplayName("UT: approve() should delegate to reviewService")
    void approve_shouldDelegate() {
        UUID specialistId = UUID.randomUUID();
        UUID id = UUID.randomUUID();

        facade.approve(specialistId, id);

        verify(reviewService).approve(specialistId, id, ApproverType.ADMIN);
    }

    @Test
    @DisplayName("UT: delete() should delete and update ratings")
    void delete_shouldDeleteAndUpdateRatings() {
        UUID specialistId = UUID.randomUUID();
        UUID id = UUID.randomUUID();
        ReviewResponseDto dto = new ReviewResponseDto(id, UUID.randomUUID(), "text", 5, null, LocalDateTime.now());
        UUID specialistCreatorId = UUID.randomUUID();

        when(reviewService.deleteById(specialistId, id)).thenReturn(dto);
        when(ratingService.updateRatingById(dto.id(), -5, OperationType.DELETE)).thenReturn(specialistCreatorId);

        facade.delete(specialistId, id);

        verify(reviewService).deleteById(specialistId, id);
        verify(ratingService).updateRatingById(dto.id(), -5, OperationType.DELETE);
        verify(creatorRatingUpdateService).updateByCreatorId(specialistCreatorId, -5, OperationType.DELETE);
    }
}
