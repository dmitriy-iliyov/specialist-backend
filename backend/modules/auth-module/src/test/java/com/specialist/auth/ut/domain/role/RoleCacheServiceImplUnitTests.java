package com.specialist.auth.ut.domain.role;

import com.specialist.auth.domain.role.Role;
import com.specialist.auth.domain.role.RoleCacheServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RoleCacheServiceImplUnitTests {

    @Mock
    private CacheManager cacheManager;

    @Mock
    private Cache cache;

    @InjectMocks
    private RoleCacheServiceImpl service;

    @Test
    @DisplayName("UT: getRoleId() when cache exists and has value should return id")
    void getRoleId_whenCacheExists_shouldReturnId() {
        Role role = Role.ROLE_USER;
        when(cacheManager.getCache("accounts:roles:id")).thenReturn(cache);
        when(cache.get(role.name(), Long.class)).thenReturn(1L);

        Long result = service.getRoleId(role);

        assertEquals(1L, result);
        verify(cacheManager).getCache("accounts:roles:id");
        verify(cache).get(role.name(), Long.class);
        verifyNoMoreInteractions(cacheManager, cache);
    }

    @Test
    @DisplayName("UT: getRoleId() when cache missing should return null")
    void getRoleId_whenCacheMissing_shouldReturnNull() {
        when(cacheManager.getCache("accounts:roles:id")).thenReturn(null);
        assertNull(service.getRoleId(Role.ROLE_USER));
        verify(cacheManager).getCache("accounts:roles:id");
        verifyNoMoreInteractions(cacheManager, cache);
    }
}
