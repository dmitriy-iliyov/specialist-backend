package com.aidcompass.specialistdirectory.domain.review.services;


import com.aidcompass.specialistdirectory.domain.review.models.dtos.ReviewResponseDto;
import com.aidcompass.specialistdirectory.domain.review.models.dtos.FullReviewResponseDto;
import com.aidcompass.specialistdirectory.domain.review.models.filters.ReviewSort;
import com.aidcompass.utils.pagination.PageResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReviewAggregatorImpl implements ReviewAggregator {

    private final ReviewService reviewService;
    private final ReviewUserAggregator reviewUserAggregator;

    @Transactional(readOnly = true)
    @Override
    public PageResponse<FullReviewResponseDto> findAllWithSortBySpecialistId(UUID specialistId, ReviewSort sort) {
        PageResponse<ReviewResponseDto> reviewsPage = reviewService.findAllWithSortBySpecialistId(specialistId, sort);
        Set<UUID> userIds = reviewsPage.data().stream().map(ReviewResponseDto::creatorId).collect(Collectors.toSet());
        Map<UUID, String> avatarUrls = reviewUserAggregator.findAvatarsByIdIn(userIds);
        Map<UUID, String> usernames = reviewUserAggregator.findUsernamesByIdIn(userIds);
        return new PageResponse<>(
                reviewsPage.data().stream()
                        .map(review -> {
                            UUID creatorId = review.creatorId();
                            return new FullReviewResponseDto(
                                    review.creatorId(),
                                    usernames.get(creatorId),
                                    avatarUrls.get(creatorId),
                                    review);
                        })
                        .toList(),
                reviewsPage.totalPage()
        );
    }
}
