package com.aidcompass.specialistdirectory.domain.review.services;

import org.springframework.scheduling.annotation.Scheduled;

import java.util.UUID;

public interface ReviewBufferService {
    void put(UUID creatorId, long rating);

    void pop(UUID creatorId);

    void markAsSent(UUID id);

    @Scheduled(
            initialDelayString = "${api.review-buffer.clean.initialDelay}",
            fixedDelayString = "${api.review-buffer.clean.fixedDelay}"
    )
    void clean();
}
