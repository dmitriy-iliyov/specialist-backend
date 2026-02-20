package com.specialist.specialistdirectory.domain.specialist.services;

import com.specialist.specialistdirectory.domain.specialist.models.dtos.ShortSpecialistInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@Slf4j
public class SpecialistCacheServiceImpl implements SpecialistCacheService {

    private final CacheManager cacheManager;
    private final RedisTemplate<String, String> redisTemplate;
    private final DefaultRedisScript<Boolean> evictAfterSaveScript;
    private final DefaultRedisScript<Boolean> evictAfterDeleteScript;

    public SpecialistCacheServiceImpl(CacheManager cacheManager, RedisTemplate<String, String> redisTemplate,
                                      @Qualifier("evictAfterSpecialistDelScript") DefaultRedisScript<Boolean> evictAfterDeleteScript,
                                      @Qualifier("evictAfterSpecialistSaveScript") DefaultRedisScript<Boolean> evictAfterSaveScript) {
        this.cacheManager = cacheManager;
        this.redisTemplate = redisTemplate;
        this.evictAfterSaveScript = evictAfterSaveScript;
        this.evictAfterDeleteScript = evictAfterDeleteScript;
    }

    @Override
    public void evictCacheAfterSave(UUID creatorId) {
        List<String> keys = new ArrayList<>(
                List.of("specialists:created:count:total::%s".formatted(creatorId),
                        "specialists:created:count:filter::%s".formatted(creatorId))
        );
        Boolean result = redisTemplate.execute(evictAfterDeleteScript, keys);
        if (result == null) {
            log.error("Script returned NULL for creatorId={}", creatorId);
        } else if (!result) {
            log.error("Script returned FALSE for creatorId={}", creatorId);
        }
    }

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
    public void evictCacheAfterDelete(UUID id, UUID creatorId) {
        List<String> keys = new ArrayList<>(
                List.of("specialists:short-info::%s".formatted(id),
                        "specialists::%s::%s".formatted(id, creatorId),
                        "specialists:created:count:total::%s".formatted(creatorId),
                        "specialists:created:count:filter::%s".formatted(creatorId))
        );
        Boolean result = redisTemplate.execute(evictAfterSaveScript, keys);
        if (result == null) {
            log.error("Script returned NULL for id={}, creatorId={}", id, creatorId);
        } else if (!result) {
            log.error("Script returned FALSE for id={}, creatorId={}", id, creatorId);
        }
    }
}