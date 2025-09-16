package com.specialist.auth.domain.authority;

import lombok.RequiredArgsConstructor;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthorityCacheServiceImpl implements AuthorityCacheService {

    private final CacheManager cacheManager;

    @Override
    public List<Long> getAuthoritiesIds(List<Authority> authorities) {
        Cache cache = cacheManager.getCache("accounts:authorities:accountId");
        if (cache != null) {
            List<Long> ids = new ArrayList<>();
            for (Authority authority: authorities) {
                Long id = cache.get(authority, Long.class);
                if (id != null) {
                    ids.add(id);
                }
            }
            return ids;
        }
        return null;
    }
}
