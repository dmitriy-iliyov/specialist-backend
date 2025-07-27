package com.aidcompass.specialistdirectory.domain.review.services;

import java.util.UUID;

public interface ReviewBufferService {
    void put(UUID creatorId, long rating);

    void pop(UUID creatorId);
}
