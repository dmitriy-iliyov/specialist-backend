package com.specialist.specialistdirectory.domain.specialist.services;

import com.specialist.specialistdirectory.domain.specialist.models.dtos.ShortSpecialistInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class SpecialistCacheServiceImpl implements SpecialistCacheService {

    private final CacheManager cacheManager;

    @Override
    public void putShortInfo(UUID id, ShortSpecialistInfo info) {
        Cache cache = cacheManager.getCache("specialists:short-info");
        if (cache != null) {
            cache.put(id.toString(), info);
        }
    }

    @Override
    public ShortSpecialistInfo getShortInfo(UUID id) {
        Cache cache = cacheManager.getCache("specialists:short-info");
        if (cache != null) {
            return cache.get(id.toString(), ShortSpecialistInfo.class);
        }
        return null;
    }

    @Override
    public void evictShortInfo(UUID id) {
        Cache cache = cacheManager.getCache("specialists:short-info");
        if (cache != null) {
            cache.evict(id.toString());
        }
    }

    @Override
    public void evictSpecialist(UUID id, UUID creatorId) {
        Cache cache = cacheManager.getCache("specialists");
        if (cache != null) {
            cache.evict("%s:%s".formatted(id, creatorId));
        }
    }

    @Override
    public void evictTotalCreatedCount(UUID creatorId) {
        Cache cache = cacheManager.getCache("specialists:created:count:total");
        if (cache != null) {
            cache.evict(creatorId.toString());
        }
    }

    @Override
    public void evictCreatedCountByFilter(UUID creatorId) {
        Cache cache = cacheManager.getCache("specialists:created:count:filter");
        if (cache != null) {
            cache.evict(creatorId.toString() + ":*");
        }
    }
}