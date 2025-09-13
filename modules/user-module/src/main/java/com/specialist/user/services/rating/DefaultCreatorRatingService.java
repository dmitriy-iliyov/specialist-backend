package com.specialist.user.services.rating;

import com.specialist.contracts.user.CreatorRatingUpdateEvent;
import com.specialist.user.exceptions.UserNotFoundByIdException;
import com.specialist.user.models.UserEntity;
import com.specialist.user.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("defaultCreatorRatingService")
@RequiredArgsConstructor
public class DefaultCreatorRatingService implements CreatorRatingService {

    private final UserRepository repository;

    @Transactional
    @Override
    public void updateById(CreatorRatingUpdateEvent dto) {
        UserEntity entity = repository.findById(dto.creatorId()).orElseThrow(UserNotFoundByIdException::new);
        long summarySpecialistRating = entity.getSummarySpecialistRating() + dto.earnedRating();
        long specialistReviewCount = entity.getSpecialistReviewCount() + dto.reviewCount();
        double creatorRating = (double) summarySpecialistRating / specialistReviewCount;
        entity.setSummarySpecialistRating(summarySpecialistRating);
        entity.setSpecialistReviewCount(specialistReviewCount);
        entity.setCreatorRating(creatorRating);
        repository.save(entity);
    }
}
