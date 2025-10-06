package com.specialist.specialistdirectory.domain.specialist.services;

import java.util.UUID;

public interface SpecialistStateService {
    void manage(UUID id, UUID ownerId);
    void recall(UUID id);
}
