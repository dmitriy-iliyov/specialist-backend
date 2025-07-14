package com.aidcompass.specialistdirectory.domain.specialist.services;

import com.aidcompass.specialistdirectory.domain.specialist.services.interfaces.SpecialistCacheService;
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
    public void putCreatorId(UUID id, UUID creatorId) {
        Cache cache = cacheManager.getCache("specialists:creator_id");
        if (cache != null) {
            cache.put(id.toString(), creatorId.toString());
        }
    }

    @Override
    public UUID getCreatorId(UUID id) {
        Cache cache = cacheManager.getCache("specialists:creator_id");
        if (cache != null) {
            return cache.get(id.toString(), UUID.class);
        }
        return null;
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