package com.specialist.specialistdirectory.domain.review.services;

import com.specialist.contracts.profile.SystemProfileService;
import com.specialist.contracts.profile.dto.UnifiedProfileResponseDto;
import com.specialist.specialistdirectory.domain.review.models.dtos.ReviewAggregatedResponseDto;
import com.specialist.specialistdirectory.domain.review.models.dtos.ReviewResponseDto;
import com.specialist.specialistdirectory.domain.review.models.enums.ReviewStatus;
import com.specialist.specialistdirectory.domain.review.models.filters.ReviewSort;
import com.specialist.utils.pagination.PageResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ReviewAggregatorImplUnitTests {

    @Mock
    private ReviewService reviewService;

    @Mock
    private SystemProfileService profileService;

    @InjectMocks
    private ReviewAggregatorImpl aggregator;

    @Test
    @DisplayName("UT: findAllWithSortBySpecialistId() should aggregate reviews and profiles")
    void findAllWithSortBySpecialistId_shouldAggregate() {
        UUID specialistId = UUID.randomUUID();
        UUID creatorId = UUID.randomUUID();
        ReviewSort sort = new ReviewSort(0, 10, true, null);
        ReviewResponseDto reviewDto = mock(ReviewResponseDto.class);
        when(reviewDto.creatorId()).thenReturn(creatorId);
        PageResponse<ReviewResponseDto> reviewsPage = new PageResponse<>(List.of(reviewDto), 1);
        UnifiedProfileResponseDto profileDto = mock(UnifiedProfileResponseDto.class);

        when(reviewService.findAllWithSortBySpecialistIdAndStatus(specialistId, ReviewStatus.APPROVED, sort))
                .thenReturn(reviewsPage);
        when(profileService.findAllByIdIn(Set.of(creatorId))).thenReturn(Map.of(creatorId, profileDto));

        PageResponse<ReviewAggregatedResponseDto> result = aggregator.findAllWithSortBySpecialistId(specialistId, sort);

        assertEquals(1, result.data().size());
        assertEquals(profileDto, result.data().get(0).user());
        assertEquals(reviewDto, result.data().get(0).review());
        verify(reviewService).findAllWithSortBySpecialistIdAndStatus(specialistId, ReviewStatus.APPROVED, sort);
        verify(profileService).findAllByIdIn(Set.of(creatorId));
    }
}
