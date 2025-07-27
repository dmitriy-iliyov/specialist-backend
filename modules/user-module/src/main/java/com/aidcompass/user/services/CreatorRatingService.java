package com.aidcompass.user.services;

import com.aidcompass.contracts.user.CreatorRatingUpdateEvent;

public interface CreatorRatingService {
    void updateById(CreatorRatingUpdateEvent dto);
}
