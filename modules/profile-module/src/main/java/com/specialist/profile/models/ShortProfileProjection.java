package com.specialist.profile.models;

import com.specialist.contracts.profile.ProfileType;

import java.util.UUID;

public interface ShortProfileProjection {
    UUID getId();
    ProfileType getType();
    String getEmail();
    String getFirstName();
    String getSecondName();
    String getLastName();
    String getAvatarUrl();
    Double getCreatorRating();

    default String getFullName() {
        if (this.getSecondName() == null)
            return this.getLastName() + " " + this.getFirstName();
        return this.getLastName() + " " + this.getFirstName() + " " + this.getSecondName();
    }
}
