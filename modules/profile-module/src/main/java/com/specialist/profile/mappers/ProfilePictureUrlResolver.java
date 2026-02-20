package com.specialist.profile.mappers;

import com.specialist.picture.PictureStorage;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class ProfilePictureUrlResolver {

    private final PictureStorage pictureStorage;

    public ProfilePictureUrlResolver(@Qualifier("profilePictureStorage") PictureStorage pictureStorage) {
        this.pictureStorage = pictureStorage;
    }

    @Named("resolvePictureUrl")
    public String resolvePictureUrl(String avatarUrl) {
        return pictureStorage.resolvePictureUrl(avatarUrl);
    }
}
