package com.specialist.auth.domain.account;

import com.specialist.auth.domain.account.services.AccountCacheServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AccountCacheServiceImplUnitTests {

    @Mock
    private CacheManager cacheManager;

    @Mock
    private Cache cache;

    @InjectMocks
    private AccountCacheServiceImpl service;

    @Test
    @DisplayName("UT: putEmailExistAs() with valid value should put to cache")
    void putEmailExistAs_withValidValue_shouldPutToCache() {
        String email = "test@example.com";
        when(cacheManager.getCache("accounts:emails")).thenReturn(cache);

        service.putEmailExistAs(email, true);

        verify(cacheManager).getCache("accounts:emails");
        verify(cache).put(email, true);
        verifyNoMoreInteractions(cacheManager, cache);
    }

    @Test
    @DisplayName("UT: putEmailExistAs() with null value should throw IllegalStateException")
    void putEmailExistAs_withNullValue_shouldThrowException() {
        String email = "test@example.com";

        assertThrows(IllegalStateException.class, () -> service.putEmailExistAs(email, null));

        verifyNoInteractions(cacheManager, cache);
    }

    @Test
    @DisplayName("UT: putEmailExistAs() when cache not found should do nothing")
    void putEmailExistAs_whenCacheNotFound_shouldDoNothing() {
        String email = "test@example.com";
        when(cacheManager.getCache("accounts:emails")).thenReturn(null);

        service.putEmailExistAs(email, true);

        verify(cacheManager).getCache("accounts:emails");
        verifyNoInteractions(cache);
        verifyNoMoreInteractions(cacheManager);
    }
}
