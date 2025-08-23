package com.specialist.auth.domain.role;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RoleCacheServiceImpl implements RoleCacheService {

    private final CacheManager cacheManager;

    @Override
    public Long getRoleId(Role role) {
        Cache cache = cacheManager.getCache("accounts:roles:id");
        if (cache != null) {
            return cache.get(role.name(), Long.class);
        }
        return null;
    }
}
