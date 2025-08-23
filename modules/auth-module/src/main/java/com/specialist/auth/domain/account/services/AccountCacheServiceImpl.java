package com.specialist.auth.domain.account.services;

import lombok.RequiredArgsConstructor;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AccountCacheServiceImpl implements AccountCacheService {

    private final CacheManager cacheManager;

    @Override
    public void putEmailAsTrue(String email) {
        Cache cache = cacheManager.getCache("accounts:emails");
        if (cache != null) {
            cache.put(email, Boolean.TRUE);
        }
    }
}
