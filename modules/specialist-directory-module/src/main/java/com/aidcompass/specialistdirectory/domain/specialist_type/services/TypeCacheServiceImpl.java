package com.aidcompass.specialistdirectory.domain.specialist_type.services;

import com.aidcompass.specialistdirectory.domain.specialist_type.models.dtos.TypeResponseDto;
import com.aidcompass.specialistdirectory.domain.specialist_type.services.interfases.TypeCacheService;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TypeCacheServiceImpl implements TypeCacheService {

    private final CacheManager cacheManager;


    @Override
    public void putToSuggestedType(TypeResponseDto dto) {
        Cache cache = cacheManager.getCache("specialists:types:suggested");
        if (cache != null) {
            cache.put(dto.id(), dto);
        }
    }

    @Override
    public void evictSuggestedType(Long id) {
        Cache cache = cacheManager.getCache("specialists:types:suggested");
        if (cache != null) {
            cache.evict(id.toString());
        }
    }

    @Override
    public void evictSuggestedTypeId(String title, boolean isApproved) {
        Cache cache = cacheManager.getCache("specialists:types:suggested:id");
        if (cache != null && isApproved) {
            cache.evict(title);
        }
    }

    @Override
    public void totalEvictSuggestedType(Long id) {
        Cache typesCache = cacheManager.getCache("specialists:types:suggested");
        Cache typesIdsCache = cacheManager.getCache("specialists:types:suggested:id");
        if (typesCache != null && typesIdsCache != null) {
            TypeResponseDto dto = typesCache.get(id, TypeResponseDto.class);
            if (dto != null && dto.title() != null) {
                typesIdsCache.evict(dto.title());
            }
            typesCache.evict(id.toString());
        }
    }

    @Override
    public void putToExists(Long id) {
        Cache cache = cacheManager.getCache("specialists:types:exists");
        if (cache != null) {
            cache.put(id, Boolean.TRUE);
        }
    }
}
