package com.specialist.specialistdirectory.ut.domain.type.services;

import com.specialist.specialistdirectory.domain.type.models.dtos.TypeResponseDto;
import com.specialist.specialistdirectory.domain.type.services.TypeCacheServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TypeCacheServiceImplUnitTests {

    @Mock
    private CacheManager cacheManager;

    @InjectMocks
    private TypeCacheServiceImpl service;

    @Test
    @DisplayName("UT: putToSuggestedType() when cache exists should put")
    void putToSuggestedType_cacheExists_shouldPut() {
        TypeResponseDto dto = mock(TypeResponseDto.class);
        when(dto.id()).thenReturn(1L);
        Cache cache = mock(Cache.class);
        when(cacheManager.getCache("specialists:types:suggested")).thenReturn(cache);

        service.putToSuggestedType(dto);

        verify(cache).put(1L, dto);
    }

    @Test
    @DisplayName("UT: putToSuggestedType() when cache missing should do nothing")
    void putToSuggestedType_cacheMissing_shouldDoNothing() {
        TypeResponseDto dto = mock(TypeResponseDto.class);
        when(cacheManager.getCache("specialists:types:suggested")).thenReturn(null);

        service.putToSuggestedType(dto);
    }

    @Test
    @DisplayName("UT: evictSuggestedType() when cache exists should evict")
    void evictSuggestedType_cacheExists_shouldEvict() {
        Cache cache = mock(Cache.class);
        when(cacheManager.getCache("specialists:types:suggested")).thenReturn(cache);

        service.evictSuggestedType(1L);

        verify(cache).evict("1");
    }

    @Test
    @DisplayName("UT: evictSuggestedType() when cache missing should do nothing")
    void evictSuggestedType_cacheMissing_shouldDoNothing() {
        when(cacheManager.getCache("specialists:types:suggested")).thenReturn(null);
        service.evictSuggestedType(1L);
    }

    @Test
    @DisplayName("UT: evictSuggestedTypeId() when cache exists and approved should evict")
    void evictSuggestedTypeId_cacheExistsApproved_shouldEvict() {
        Cache cache = mock(Cache.class);
        when(cacheManager.getCache("specialists:types:suggested:id")).thenReturn(cache);

        service.evictSuggestedTypeId("title", true);

        verify(cache).evict("title");
    }

    @Test
    @DisplayName("UT: evictSuggestedTypeId() when not approved should do nothing")
    void evictSuggestedTypeId_notApproved_shouldDoNothing() {
        Cache cache = mock(Cache.class);
        when(cacheManager.getCache("specialists:types:suggested:id")).thenReturn(cache);
        
        service.evictSuggestedTypeId("title", false);
        
        verify(cacheManager).getCache("specialists:types:suggested:id");
        verify(cache, never()).evict(any());
    }

    @Test
    @DisplayName("UT: totalEvictSuggestedType() when caches exist and entry exists should evict both")
    void totalEvictSuggestedType_allExist_shouldEvictBoth() {
        Cache typesCache = mock(Cache.class);
        Cache typesIdsCache = mock(Cache.class);
        TypeResponseDto dto = mock(TypeResponseDto.class);
        when(dto.title()).thenReturn("title");

        when(cacheManager.getCache("specialists:types:suggested")).thenReturn(typesCache);
        when(cacheManager.getCache("specialists:types:suggested:id")).thenReturn(typesIdsCache);
        when(typesCache.get(1L, TypeResponseDto.class)).thenReturn(dto);

        service.totalEvictSuggestedType(1L);

        verify(typesIdsCache).evict("title");
        verify(typesCache).evict("1");
    }

    @Test
    @DisplayName("UT: totalEvictSuggestedType() when caches missing should do nothing")
    void totalEvictSuggestedType_cachesMissing_shouldDoNothing() {
        when(cacheManager.getCache("specialists:types:suggested")).thenReturn(null);
        service.totalEvictSuggestedType(1L);
    }

    @Test
    @DisplayName("UT: putToExists() when cache exists should put")
    void putToExists_cacheExists_shouldPut() {
        Cache cache = mock(Cache.class);
        when(cacheManager.getCache("specialists:types:exists")).thenReturn(cache);

        service.putToExists(1L);

        verify(cache).put(1L, Boolean.TRUE);
    }

    @Test
    @DisplayName("UT: putToExists() when cache missing should do nothing")
    void putToExists_cacheMissing_shouldDoNothing() {
        when(cacheManager.getCache("specialists:types:exists")).thenReturn(null);
        service.putToExists(1L);
    }
}
