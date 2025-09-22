package com.specialist.specialistdirectory.domain.review.services;

import java.util.UUID;

public interface AdminReviewManagementFacade {
    void delete(UUID specialistId, UUID id);
}
