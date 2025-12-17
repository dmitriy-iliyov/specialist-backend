package com.specialist.specialistdirectory.domain.review.mappers;

import com.specialist.picture.PictureStorage;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class ReviewPictureUrlResolver {

    private final PictureStorage pictureStorage;

    public ReviewPictureUrlResolver(@Qualifier("reviewPictureStorage") PictureStorage pictureStorage) {
        this.pictureStorage = pictureStorage;
    }

    @Named("resolvePictureUrl")
    public String resolvePictureUrl(String avatarUrl) {
        return pictureStorage.resolvePictureUrl(avatarUrl);
    }
}
