package com.specialist.auth.domain.refresh_token;

import com.specialist.auth.core.BaseUserDetails;
import com.specialist.auth.domain.refresh_token.models.RefreshToken;
import com.specialist.auth.domain.refresh_token.models.RefreshTokenEntity;
import com.specialist.auth.exceptions.RefreshTokenNotFoundByIdException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.time.Instant;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RefreshTokenServiceImplUnitTests {

    @Mock
    private RefreshTokenRepository repository;

    @InjectMocks
    private RefreshTokenServiceImpl service;

    @Mock
    private BaseUserDetails userDetails;

    @Mock
    private RefreshTokenCacheService cacheService;

    @Test
    @DisplayName("UT: generateAndSave() should save token and put into cache")
    void generateAndSave_shouldSaveTokenAndPutInCache() {
        UUID userId = UUID.randomUUID();
        Collection<? extends GrantedAuthority> authorities =
                List.of(new SimpleGrantedAuthority("ROLE_USER"));
        RefreshToken refreshToken = new RefreshToken(UUID.randomUUID(), userId, List.of("ROLE_USER"), Instant.now());
        when(userDetails.getId()).thenReturn(userId);
        when(userDetails.getAuthorities()).thenReturn((Collection) authorities);
        when(cacheService.putToActiveAsTrue(any(RefreshToken.class))).thenReturn(refreshToken);
        when(repository.save(any(RefreshTokenEntity.class))).thenReturn(new RefreshTokenEntity(userId, "ROLE_USER", Instant.now(), Instant.now()));

        service.TOKEN_TTL = 3600L;

        RefreshToken result = service.generateAndSave(userDetails);

        assertNotNull(result);
        assertEquals(userId, result.accountId());

        verify(userDetails, times(1)).getId();
        verify(userDetails, times(1)).getAuthorities();
        verify(repository, times(1)).save(any(RefreshTokenEntity.class));
        verify(cacheService, times(1)).putToActiveAsTrue(any(RefreshToken.class));
    }

    @Test
    @DisplayName("UT: isActiveById() from cache should return cached value")
    void isActiveById_fromCache_shouldReturnCachedValue() {
        UUID id = UUID.randomUUID();
        when(cacheService.isActiveById(id)).thenReturn(true);

        boolean active = service.isActiveById(id);

        assertTrue(active);
        verify(cacheService, times(1)).isActiveById(id);
        verifyNoInteractions(repository);
    }

    @Test
    @DisplayName("UT: isActiveById() when token expired in DB should return false and update cache")
    void isActiveById_whenTokenExpiredInDB_shouldReturnFalseAndUpdateCache() {
        UUID id = UUID.randomUUID();
        RefreshTokenEntity expiredEntity = new RefreshTokenEntity(
                UUID.randomUUID(), "ROLE_USER", Instant.now(), Instant.now().minusSeconds(100)
        );
        expiredEntity.setId(id);

        when(cacheService.isActiveById(id)).thenReturn(null);
        when(repository.findById(id)).thenReturn(Optional.of(expiredEntity));

        boolean active = service.isActiveById(id);

        assertFalse(active);
        verify(cacheService, times(1)).isActiveById(id);
        verify(repository, times(1)).findById(id);
        verify(cacheService, times(1)).putToActiveAsFalse(id);
    }

    @Test
    @DisplayName("UT: isActiveById() when token not found in DB should throw exception")
    void isActiveById_whenTokenNotFoundInDB_shouldThrowException() {
        UUID id = UUID.randomUUID();
        when(cacheService.isActiveById(id)).thenReturn(null);
        when(repository.findById(id)).thenReturn(Optional.empty());

        assertThrows(RefreshTokenNotFoundByIdException.class, () -> service.isActiveById(id));

        verify(cacheService, times(1)).isActiveById(id);
        verify(repository, times(1)).findById(id);
    }

    @Test
    @DisplayName("UT: findById() when token exists should return mapped RefreshToken")
    void findById_whenExists_shouldReturnToken() {
        UUID id = UUID.randomUUID();
        RefreshTokenEntity entity = new RefreshTokenEntity(
                id,
                UUID.randomUUID(),
                "ROLE_USER,ROLE_ADMIN",
                Instant.now(),
                Instant.now().plusSeconds(3600)
        );

        when(repository.findById(id)).thenReturn(Optional.of(entity));

        RefreshToken result = service.findById(id);

        assertNotNull(result);
        assertEquals(id, result.id());
        assertEquals(List.of("ROLE_USER", "ROLE_ADMIN"), result.authorities());

        verify(repository, times(1)).findById(id);
    }

    @Test
    @DisplayName("UT: findById() when token not found should return null")
    void findById_whenNotFound_shouldReturnNull() {
        UUID id = UUID.randomUUID();
        when(repository.findById(id)).thenReturn(Optional.empty());

        RefreshToken refreshToken = service.findById(id);
        assertNull(refreshToken);

        verify(repository, times(1)).findById(id);
    }

    @Test
    @DisplayName("UT: deleteById() should delete from repo and evict cache")
    void deleteById_shouldDeleteFromRepoAndEvictCache() {
        UUID id = UUID.randomUUID();

        service.deleteById(id);

        verify(repository, times(1)).deleteById(id);
    }

    @Test
    @DisplayName("UT: deleteAllByAccountId() with empty list should call cache evict and repo delete")
    void deleteAllByAccountId_withEmptyList_shouldCallCacheEvictAndRepoDelete() {
        UUID accountId = UUID.randomUUID();
        when(repository.findAllIdByAccountId(accountId)).thenReturn(Collections.emptyList());

        service.deleteAllByAccountId(accountId);

        verify(repository, times(1)).findAllIdByAccountId(accountId);
        verify(cacheService, times(1)).evictAllByIdIn(Collections.emptySet());
        verify(repository, times(1)).deleteAllByAccountId(accountId);
    }
}
