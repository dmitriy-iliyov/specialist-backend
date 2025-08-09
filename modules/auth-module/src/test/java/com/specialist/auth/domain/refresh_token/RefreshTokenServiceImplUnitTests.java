package com.specialist.auth.domain.refresh_token;

import com.specialist.auth.core.models.BaseUserDetails;
import com.specialist.auth.domain.refresh_token.models.RefreshToken;
import com.specialist.auth.domain.refresh_token.models.RefreshTokenEntity;
import com.specialist.auth.domain.refresh_token.models.RefreshTokenStatus;
import com.specialist.auth.exceptions.RefreshTokenNotFoundByIdException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.time.Instant;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RefreshTokenServiceImplUnitTests {

    @Mock
    RefreshTokenRepository repository;

    @Mock
    CacheManager cacheManager;

    @InjectMocks
    RefreshTokenServiceImpl service;

    @Mock
    BaseUserDetails userDetails;

    @Mock
    Cache cache;

    @Test
    @DisplayName("UT: generateAndSave() should save token and put into cache")
    void generateAndSave_shouldSaveTokenAndPutInCache() {
        UUID userId = UUID.randomUUID();
        Collection authorities =
                List.of(new SimpleGrantedAuthority("ROLE_USER"));
        when(userDetails.getId()).thenReturn(userId);
        when(userDetails.getAuthorities()).thenReturn(authorities);
        when(cacheManager.getCache("refresh-tokens:active")).thenReturn(cache);
        service.TOKEN_TTL = 3600L;

        RefreshToken result = service.generateAndSave(userDetails);

        assertNotNull(result);
        assertEquals(userId, result.subjectId());
        assertEquals(RefreshTokenStatus.ACTIVE, result.status());

        verify(userDetails, times(1)).getId();
        verify(userDetails, times(1)).getAuthorities();
        verify(repository, times(1)).save(any(RefreshTokenEntity.class));
        verify(cacheManager, times(1)).getCache("refresh-tokens:active");
        verify(cache, times(1)).put(eq(result.id()), eq(Boolean.TRUE));
        verifyNoMoreInteractions(repository, cacheManager, cache, userDetails);
    }

    @Test
    @DisplayName("UT: generateAndSave() should save token and NOT put into cache")
    void generateAndSave_whenCacheNull_shouldSaveTokenAndPutInCache() {
        UUID userId = UUID.randomUUID();
        Collection authorities =
                List.of(new SimpleGrantedAuthority("ROLE_USER"));
        when(userDetails.getId()).thenReturn(userId);
        when(userDetails.getAuthorities()).thenReturn(authorities);
        when(cacheManager.getCache("refresh-tokens:active")).thenReturn(null);
        service.TOKEN_TTL = 3600L;

        RefreshToken result = service.generateAndSave(userDetails);

        assertNotNull(result);
        assertEquals(userId, result.subjectId());
        assertEquals(RefreshTokenStatus.ACTIVE, result.status());

        verify(userDetails, times(1)).getId();
        verify(userDetails, times(1)).getAuthorities();
        verify(repository, times(1)).save(any(RefreshTokenEntity.class));
        verify(cacheManager, times(1)).getCache("refresh-tokens:active");
        verify(cache, times(0)).put(eq(result.id()), eq(Boolean.TRUE));
        verifyNoMoreInteractions(repository, cacheManager, cache, userDetails);
    }

    @Test
    @DisplayName("UT: isActiveById() should delegate to repository")
    void isActiveById_shouldReturnRepositoryValue() {
        UUID id = UUID.randomUUID();
        when(repository.existsByIdAndStatus(id, RefreshTokenStatus.ACTIVE)).thenReturn(true);

        boolean active = service.isActiveById(id);

        assertTrue(active);
        verify(repository, times(1)).existsByIdAndStatus(id, RefreshTokenStatus.ACTIVE);
        verifyNoMoreInteractions(repository);
        verifyNoInteractions(cacheManager);
    }

    @Test
    @DisplayName("UT: findById() when token exists should return mapped RefreshToken")
    void findById_whenExists_shouldReturnToken() {
        UUID id = UUID.randomUUID();
        RefreshTokenEntity entity = new RefreshTokenEntity(
                id,
                UUID.randomUUID(),
                "ROLE_USER,ROLE_ADMIN",
                RefreshTokenStatus.ACTIVE,
                Instant.now(),
                Instant.now().plusSeconds(3600)
        );

        when(repository.findById(id)).thenReturn(Optional.of(entity));

        RefreshToken result = service.findById(id);

        assertNotNull(result);
        assertEquals(id, result.id());
        assertEquals(List.of("ROLE_USER", "ROLE_ADMIN"), result.authorities());

        verify(repository, times(1)).findById(id);
        verifyNoMoreInteractions(repository);
        verifyNoInteractions(cacheManager);
    }

    @Test
    @DisplayName("UT: findById() when token not found should throw exception")
    void findById_whenNotFound_shouldThrow() {
        UUID id = UUID.randomUUID();
        when(repository.findById(id)).thenReturn(Optional.empty());

        assertThrows(RefreshTokenNotFoundByIdException.class, () -> service.findById(id));

        verify(repository, times(1)).findById(id);
        verifyNoMoreInteractions(repository);
        verifyNoInteractions(cacheManager);
    }

    @Test
    @DisplayName("UT: deactivateById() should update status and put false into cache")
    void deactivateById_shouldUpdateStatusAndPutFalseInCache() {
        UUID id = UUID.randomUUID();
        when(cacheManager.getCache("refresh-tokens:active")).thenReturn(cache);

        service.deactivateById(id);

        verify(repository, times(1)).updateStatusById(id, RefreshTokenStatus.DEACTIVATED);
        verify(cacheManager, times(1)).getCache("refresh-tokens:active");
        verify(cache, times(1)).put(id, Boolean.FALSE);
        verifyNoMoreInteractions(repository, cacheManager, cache);
    }

    @Test
    @DisplayName("UT: deactivateById() should update status and NOT put false into cache")
    void deactivateById_whenCacheNull_shouldUpdateStatusAndPutFalseInCache() {
        UUID id = UUID.randomUUID();
        when(cacheManager.getCache("refresh-tokens:active")).thenReturn(null);

        service.deactivateById(id);

        verify(repository, times(1)).updateStatusById(id, RefreshTokenStatus.DEACTIVATED);
        verify(cacheManager, times(1)).getCache("refresh-tokens:active");
        verify(cache, times(0)).put(id, Boolean.FALSE);
        verifyNoMoreInteractions(repository, cacheManager, cache);
    }

    @Test
    @DisplayName("UT: revokeById() should update status and put false into cache")
    void revokeById_shouldUpdateStatusAndPutFalseInCache() {
        UUID id = UUID.randomUUID();
        when(cacheManager.getCache("refresh-tokens:active")).thenReturn(cache);

        service.revokeById(id);

        verify(repository, times(1)).updateStatusById(id, RefreshTokenStatus.REVOKED);
        verify(cacheManager, times(1)).getCache("refresh-tokens:active");
        verify(cache, times(1)).put(id, Boolean.FALSE);
        verifyNoMoreInteractions(repository, cacheManager, cache);
    }

    @Test
    @DisplayName("UT: revokeById() should update status and NOT put false into cache")
    void revokeById_whenCacheNull_shouldUpdateStatusAndPutFalseInCache() {
        UUID id = UUID.randomUUID();
        when(cacheManager.getCache("refresh-tokens:active")).thenReturn(null);

        service.revokeById(id);

        verify(repository, times(1)).updateStatusById(id, RefreshTokenStatus.REVOKED);
        verify(cacheManager, times(1)).getCache("refresh-tokens:active");
        verify(cache, times(0)).put(id, Boolean.FALSE);
        verifyNoMoreInteractions(repository, cacheManager, cache);
    }
}