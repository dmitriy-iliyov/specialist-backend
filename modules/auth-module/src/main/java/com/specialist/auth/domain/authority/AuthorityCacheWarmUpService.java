package com.specialist.auth.domain.authority;

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
public final class AuthorityCacheWarmUpService {

    private final CacheManager cacheManager;
    private final AuthorityService service;

    @EventListener(ApplicationReadyEvent.class)
    public void warmUp() {
        Cache cache = cacheManager.getCache("accounts:authorities:id");
        if (cache != null) {
            for (AuthorityEntity entity: service.findAll()) {
                cache.put(entity.getFieldAuthority(), entity.getId());
            }
        } else {
            log.error("Cache with name accounts:authorities:id is null");
            return;
        }
        log.info("Authority cache successfully wormed up");
    }
}
