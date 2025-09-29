package com.specialist.specialistdirectory.domain.review.services;

import com.specialist.contracts.profile.CreatorRatingUpdateEvent;

public interface CreatorRatingEventSender {
    void sendEvent(CreatorRatingUpdateEvent event, int attemptNumber);
}
