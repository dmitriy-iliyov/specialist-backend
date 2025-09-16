package com.specialist.auth.domain.role;

import lombok.RequiredArgsConstructor;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RoleCacheServiceImpl implements RoleCacheService {

    private final CacheManager cacheManager;

    @Override
    public Long getRoleId(Role role) {
        Cache cache = cacheManager.getCache("accounts:roles:accountId");
        if (cache != null) {
            return cache.get(role.name(), Long.class);
        }
        return null;
    }
}
