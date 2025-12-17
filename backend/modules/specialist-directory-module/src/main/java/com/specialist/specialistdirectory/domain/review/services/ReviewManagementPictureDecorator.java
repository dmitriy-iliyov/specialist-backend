package com.specialist.specialistdirectory.domain.review.services;

import com.specialist.picture.PictureStorage;
import com.specialist.specialistdirectory.domain.review.models.dtos.ReviewCreateRequest;
import com.specialist.specialistdirectory.domain.review.models.dtos.ReviewDeleteRequest;
import com.specialist.specialistdirectory.domain.review.models.dtos.ReviewResponseDto;
import com.specialist.specialistdirectory.domain.review.models.dtos.ReviewUpdateRequest;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class ReviewManagementPictureDecorator implements ReviewManagementFacade {

    private final ReviewManagementFacade delegate;
    private final PictureStorage pictureStorage;
    private final ReviewService reviewService;

    public ReviewManagementPictureDecorator(@Qualifier("reviewManagementRetryDecorator") ReviewManagementFacade delegate,
                                            PictureStorage pictureStorage, ReviewService reviewService) {
        this.delegate = delegate;
        this.pictureStorage = pictureStorage;
        this.reviewService = reviewService;
    }

    @Override
    public ReviewResponseDto save(ReviewCreateRequest request) {
        ReviewResponseDto dto = delegate.save(request);
        if (request.picture() != null) {
            String url = pictureStorage.save(request.picture(), dto.id());
            reviewService.updatePictureUrlById(dto.id(), url);
        }
        return dto;
    }

    @Override
    public ReviewResponseDto update(ReviewUpdateRequest request) {
        ReviewResponseDto dto = delegate.update(request);
        if (request.picture() != null) {
            String url = pictureStorage.save(request.picture(), dto.id());
            reviewService.updatePictureUrlById(dto.id(), url);
        }
        return dto;
    }

    @Override
    public void delete(ReviewDeleteRequest request) {
        delegate.delete(request);
        pictureStorage.deleteByAggregateId(request.id());
    }
}
