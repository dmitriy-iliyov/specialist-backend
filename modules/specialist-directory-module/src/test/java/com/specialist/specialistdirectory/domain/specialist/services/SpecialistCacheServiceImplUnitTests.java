package com.specialist.specialistdirectory.domain.specialist.services;

import com.specialist.specialistdirectory.domain.specialist.models.dtos.ShortSpecialistInfo;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SpecialistCacheServiceImplUnitTests {

    @Mock
    private CacheManager cacheManager;
    @Mock
    private RedisTemplate<String, String> redisTemplate;
    @Mock
    private DefaultRedisScript<Boolean> evictAfterSaveScript;
    @Mock
    private DefaultRedisScript<Boolean> evictAfterDeleteScript;

    @InjectMocks
    private SpecialistCacheServiceImpl service;

    @Test
    @DisplayName("UT: evictCacheAfterSave() should execute script")
    void evictCacheAfterSave_shouldExecuteScript() {
        UUID creatorId = UUID.randomUUID();
        when(redisTemplate.execute(any(), any(List.class))).thenReturn(true);

        service.evictCacheAfterSave(creatorId);

        verify(redisTemplate).execute(any(), any(List.class));
    }

    @Test
    @DisplayName("UT: evictCacheAfterSave() when script returns null should not throw")
    void evictCacheAfterSave_nullResult_shouldNotThrow() {
        UUID creatorId = UUID.randomUUID();
        when(redisTemplate.execute(any(), any(List.class))).thenReturn(null);

        service.evictCacheAfterSave(creatorId);
    }

    @Test
    @DisplayName("UT: evictCacheAfterSave() when script returns false should not throw")
    void evictCacheAfterSave_falseResult_shouldNotThrow() {
        UUID creatorId = UUID.randomUUID();
        when(redisTemplate.execute(any(), any(List.class))).thenReturn(false);

        service.evictCacheAfterSave(creatorId);
    }

    @Test
    @DisplayName("UT: putShortInfo() when cache exists should put")
    void putShortInfo_cacheExists_shouldPut() {
        UUID id = UUID.randomUUID();
        ShortSpecialistInfo info = mock(ShortSpecialistInfo.class);
        Cache cache = mock(Cache.class);
        when(cacheManager.getCache("specialists:short-info")).thenReturn(cache);

        service.putShortInfo(id, info);

        verify(cache).put(id.toString(), info);
    }

    @Test
    @DisplayName("UT: putShortInfo() when cache missing should do nothing")
    void putShortInfo_cacheMissing_shouldDoNothing() {
        UUID id = UUID.randomUUID();
        ShortSpecialistInfo info = mock(ShortSpecialistInfo.class);
        when(cacheManager.getCache("specialists:short-info")).thenReturn(null);

        service.putShortInfo(id, info);
    }

    @Test
    @DisplayName("UT: getShortInfo() when cache exists and value exists should return value")
    void getShortInfo_cacheExists_valueExists_shouldReturn() {
        UUID id = UUID.randomUUID();
        ShortSpecialistInfo info = mock(ShortSpecialistInfo.class);
        Cache cache = mock(Cache.class);
        when(cacheManager.getCache("specialists:short-info")).thenReturn(cache);
        when(cache.get(id.toString(), ShortSpecialistInfo.class)).thenReturn(info);

        ShortSpecialistInfo result = service.getShortInfo(id);

        assertEquals(info, result);
    }

    @Test
    @DisplayName("UT: getShortInfo() when cache missing should return null")
    void getShortInfo_cacheMissing_shouldReturnNull() {
        UUID id = UUID.randomUUID();
        when(cacheManager.getCache("specialists:short-info")).thenReturn(null);

        ShortSpecialistInfo result = service.getShortInfo(id);

        assertNull(result);
    }

    @Test
    @DisplayName("UT: evictCacheAfterDelete() should execute script")
    void evictCacheAfterDelete_shouldExecuteScript() {
        UUID id = UUID.randomUUID();
        UUID creatorId = UUID.randomUUID();
        when(redisTemplate.execute(any(), any(List.class))).thenReturn(true);

        service.evictCacheAfterDelete(id, creatorId);

        verify(redisTemplate).execute(any(), any(List.class));
    }

    @Test
    @DisplayName("UT: evictCacheAfterDelete() when script returns null should not throw")
    void evictCacheAfterDelete_nullResult_shouldNotThrow() {
        UUID id = UUID.randomUUID();
        UUID creatorId = UUID.randomUUID();
        when(redisTemplate.execute(any(), any(List.class))).thenReturn(null);

        service.evictCacheAfterDelete(id, creatorId);
    }

    @Test
    @DisplayName("UT: evictCacheAfterDelete() when script returns false should not throw")
    void evictCacheAfterDelete_falseResult_shouldNotThrow() {
        UUID id = UUID.randomUUID();
        UUID creatorId = UUID.randomUUID();
        when(redisTemplate.execute(any(), any(List.class))).thenReturn(false);

        service.evictCacheAfterDelete(id, creatorId);
    }
}
