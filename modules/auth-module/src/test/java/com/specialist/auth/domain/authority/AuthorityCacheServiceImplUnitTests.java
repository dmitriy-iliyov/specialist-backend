package com.specialist.auth.domain.authority;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthorityCacheServiceImplUnitTests {

    @Mock
    private CacheManager cacheManager;

    @Mock
    private Cache cache;

    @InjectMocks
    private AuthorityCacheServiceImpl service;

    @Test
    @DisplayName("UT: getAuthoritiesIds() when cache exists and has values should return ids")
    void getAuthoritiesIds_whenCacheExists_shouldReturnIds() {
        List<Authority> authorities = List.of(Authority.ACCOUNT_CREATE);
        when(cacheManager.getCache("accounts:authorities:id")).thenReturn(cache);
        when(cache.get(Authority.ACCOUNT_CREATE, Long.class)).thenReturn(1L);

        List<Long> result = service.getAuthoritiesIds(authorities);

        assertEquals(1, result.size());
        assertEquals(1L, result.get(0));
        verify(cacheManager).getCache("accounts:authorities:id");
        verify(cache).get(Authority.ACCOUNT_CREATE, Long.class);
        verifyNoMoreInteractions(cacheManager, cache);
    }

    @Test
    @DisplayName("UT: getAuthoritiesIds() when cache missing should return null")
    void getAuthoritiesIds_whenCacheMissing_shouldReturnNull() {
        when(cacheManager.getCache("accounts:authorities:id")).thenReturn(null);
        assertNull(service.getAuthoritiesIds(List.of(Authority.ACCOUNT_CREATE)));
        verify(cacheManager).getCache("accounts:authorities:id");
        verifyNoMoreInteractions(cacheManager, cache);
    }
}
