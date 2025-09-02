package com.specialist.specialistdirectory.domain.specialist.services;

import com.specialist.specialistdirectory.domain.review.models.enums.OperationType;
import com.specialist.specialistdirectory.domain.specialist.models.SpecialistEntity;
import com.specialist.specialistdirectory.domain.specialist.repositories.SpecialistRepository;
import com.specialist.specialistdirectory.exceptions.SpecialistNotFoundByIdException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DefaultSpecialistRatingService implements SpecialistRatingService {

    private final SpecialistRepository repository;

    @Transactional
    @Override
    public void updateRatingById(UUID id, long earnedRating, OperationType operationType) {
        SpecialistEntity entity = repository.findById(id).orElseThrow(SpecialistNotFoundByIdException::new);
        switch (operationType) {
            case PERSIST -> {
                long summaryRating = entity.getSummaryRating() + earnedRating;
                long reviewsCount = entity.getReviewsCount() + 1;
                double rating = (double) summaryRating / reviewsCount;
                entity.setSummaryRating(summaryRating);
                entity.setReviewsCount(reviewsCount);
                entity.setRating(rating);
                repository.save(entity);
            }
            case UPDATE -> {
                long summaryRating = entity.getSummaryRating()  + earnedRating;
                long reviewsCount = entity.getReviewsCount();
                double rating = (double) summaryRating / reviewsCount;
                entity.setSummaryRating(summaryRating);
                entity.setReviewsCount(reviewsCount);
                entity.setRating(rating);
                repository.save(entity);
            }
            case DELETE -> {
                long summaryRating = entity.getSummaryRating() + earnedRating;
                long reviewsCount = entity.getReviewsCount() - 1;
                double rating = (double) summaryRating / reviewsCount;
                entity.setSummaryRating(summaryRating);
                entity.setReviewsCount(reviewsCount);
                entity.setRating(rating);
                repository.save(entity);
            }
        }
    }
}
