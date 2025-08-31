package com.specialist.specialistdirectory.domain.specialist.services;


import com.specialist.specialistdirectory.domain.specialist.models.dtos.ShortSpecialistInfo;

import java.util.UUID;

public interface SpecialistCacheService {
    void putShortInfo(UUID id, ShortSpecialistInfo creatorId);

    ShortSpecialistInfo getShortInfo(UUID id);

    void evictSpecialist(UUID id, UUID creatorId);

    void evictTotalCreatedCount(UUID creatorId);

    void evictCreatedCountByFilter(UUID creatorId);
}
