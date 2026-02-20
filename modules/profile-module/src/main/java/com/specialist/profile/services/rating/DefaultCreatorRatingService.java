package com.specialist.profile.services.rating;

import com.specialist.contracts.profile.CreatorRatingUpdateEvent;
import com.specialist.profile.exceptions.UserNotFoundByIdException;
import com.specialist.profile.models.UserProfileEntity;
import com.specialist.profile.repositories.UserProfileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("defaultCreatorRatingService")
@RequiredArgsConstructor
public class DefaultCreatorRatingService implements CreatorRatingService {

    private final UserProfileRepository repository;

    @Transactional
    @Override
    public void updateById(CreatorRatingUpdateEvent dto) {
        UserProfileEntity entity = repository.findById(dto.creatorId()).orElseThrow(UserNotFoundByIdException::new);
        long summarySpecialistRating = entity.getSummarySpecialistRating() + dto.earnedRating();
        long specialistReviewCount = entity.getSpecialistReviewCount() + dto.reviewCount();
        double creatorRating = (double) summarySpecialistRating / specialistReviewCount;
        entity.setSummarySpecialistRating(summarySpecialistRating);
        entity.setSpecialistReviewCount(specialistReviewCount);
        entity.setCreatorRating(creatorRating);
        repository.save(entity);
    }
}
