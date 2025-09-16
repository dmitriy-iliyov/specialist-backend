package com.specialist.auth.domain.role;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

@Profile("cache-warm-up")
@Service
@RequiredArgsConstructor
@Slf4j
public final class RoleCacheWarmUpService {

    private final RoleService service;
    private final CacheManager cacheManager;

    @EventListener(ApplicationReadyEvent.class)
    public void warmUp() {
        Cache cache = cacheManager.getCache("accounts:roles:accountId");
        if (cache != null) {
            for (RoleEntity entity: service.findAll()) {
                cache.put(entity.getRole().name(), entity.getId());
            }
        } else {
            log.error("Cache with name 'accounts:roles:accountId' is null");
            return;
        }
        log.info("Role cache successfully wormed up");
    }
}
