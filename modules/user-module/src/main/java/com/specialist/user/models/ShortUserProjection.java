package com.specialist.user.models;

import com.specialist.contracts.user.UserType;

import java.util.UUID;

public interface ShortUserProjection {
    UUID getId();
    UserType getType();
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
