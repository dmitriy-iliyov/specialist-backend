package com.specialist.user.services.rating;

import com.specialist.contracts.user.CreatorRatingUpdateEvent;

public interface CreatorRatingService {
    void updateById(CreatorRatingUpdateEvent dto);
}
