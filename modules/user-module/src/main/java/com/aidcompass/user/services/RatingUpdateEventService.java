package com.aidcompass.user.services;

import com.aidcompass.contracts.user.CreatorRatingUpdateEvent;

import java.util.UUID;

public interface RatingUpdateEventService {
    void save(CreatorRatingUpdateEvent event);

    boolean existsById(UUID id);

    void clean();
}
