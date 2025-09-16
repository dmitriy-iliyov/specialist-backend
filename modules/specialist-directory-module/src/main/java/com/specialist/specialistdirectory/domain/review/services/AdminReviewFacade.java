package com.specialist.specialistdirectory.domain.review.services;

import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

public interface AdminReviewFacade {
    void delete(UUID specialistId, UUID id);
}
