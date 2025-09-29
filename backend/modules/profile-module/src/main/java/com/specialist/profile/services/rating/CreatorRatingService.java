package com.specialist.profile.services.rating;

import com.specialist.contracts.profile.CreatorRatingUpdateEvent;

public interface CreatorRatingService {
    void updateById(CreatorRatingUpdateEvent dto);
}
