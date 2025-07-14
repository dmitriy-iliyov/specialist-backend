package com.aidcompass.specialistdirectory.domain.specialist.services.interfaces;


import java.util.UUID;

public interface SpecialistCacheService {
    void putCreatorId(UUID id, UUID creatorId);

    UUID getCreatorId(UUID id);

    void evictSpecialist(UUID id, UUID creatorId);

    void evictTotalCreatedCount(UUID creatorId);

    void evictCreatedCountByFilter(UUID creatorId);
}
