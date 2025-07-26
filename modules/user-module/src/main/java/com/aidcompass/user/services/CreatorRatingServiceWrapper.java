package com.aidcompass.user.services;

import jakarta.persistence.OptimisticLockException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class CreatorRatingServiceWrapper implements CreatorRatingService {

    private final CreatorRatingService realService;

    @Autowired
    public CreatorRatingServiceWrapper(@Qualifier("unifiedUserService") CreatorRatingService realService) {
        this.realService = realService;
    }

    @Override
    public void updateCreatorRatingById(int reviewCount, long summaryRating) {
        try {
            realService.updateCreatorRatingById(reviewCount, summaryRating);
        } catch (OptimisticLockException e) {
            realService.updateCreatorRatingById(reviewCount, summaryRating);
        }
    }
}
