package com.specialist.user.services;

import com.specialist.contracts.user.CreatorRatingUpdateEvent;

public interface CreatorRatingService {
    void updateById(CreatorRatingUpdateEvent dto);
}
