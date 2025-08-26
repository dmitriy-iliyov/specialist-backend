package com.specialist.auth.domain.refresh_token;

import com.specialist.auth.core.models.BaseUserDetails;
import com.specialist.auth.domain.refresh_token.models.RefreshToken;
import com.specialist.auth.domain.refresh_token.models.RefreshTokenEntity;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.time.Instant;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RefreshTokenServiceImplUnitTests {

    @Mock
    RefreshTokenRepository repository;

    @InjectMocks
    RefreshTokenServiceImpl service;

    @Mock
    BaseUserDetails userDetails;

    @Mock
    RefreshTokenCacheService cacheService;

    @Test
    @DisplayName("UT: generateAndSave() should save token and put into cache")
    void generateAndSave_shouldSaveTokenAndPutInCache() {
        UUID userId = UUID.randomUUID();
        Collection authorities =
                List.of(new SimpleGrantedAuthority("ROLE_USER"));
        RefreshToken refreshToken = new RefreshToken(UUID.randomUUID(), userId, List.of("ROLE_USER"), Instant.now());
        when(userDetails.getId()).thenReturn(userId);
        when(userDetails.getAuthorities()).thenReturn(authorities);
        when(cacheService.putToActiveAsTrue(any(RefreshToken.class))).thenReturn(refreshToken);
        when(repository.save(any(RefreshTokenEntity.class))).thenReturn(new RefreshTokenEntity(UUID.randomUUID(), userId, "ROLE_USER", Instant.now(), Instant.now()));

        service.TOKEN_TTL = 3600L;

        RefreshToken result = service.generateAndSave(userDetails);

        assertNotNull(result);
        assertEquals(userId, result.accountId());

        verify(userDetails, times(1)).getId();
        verify(userDetails, times(1)).getAuthorities();
        verify(repository, times(1)).save(any(RefreshTokenEntity.class));
        verify(cacheService, times(1)).putToActiveAsTrue(any(RefreshToken.class));
        verifyNoMoreInteractions(repository, cacheService, userDetails);
    }

    @Test
    @DisplayName("UT: generateAndSave() should save token and NOT put into cache")
    void generateAndSave_whenCacheNull_shouldSaveTokenAndPutInCache() {
        UUID userId = UUID.randomUUID();
        Collection authorities =
                List.of(new SimpleGrantedAuthority("ROLE_USER"));
        RefreshToken refreshToken = new RefreshToken(UUID.randomUUID(), userId, List.of("ROLE_USER"), Instant.now());
        when(userDetails.getId()).thenReturn(userId);
        when(userDetails.getAuthorities()).thenReturn(authorities);
        when(cacheService.putToActiveAsTrue(any(RefreshToken.class))).thenReturn(refreshToken);
        when(repository.save(any(RefreshTokenEntity.class))).thenReturn(new RefreshTokenEntity(UUID.randomUUID(), userId, "ROLE_USER", Instant.now(), Instant.now()));
        service.TOKEN_TTL = 3600L;

        RefreshToken result = service.generateAndSave(userDetails);

        assertNotNull(result);
        assertEquals(userId, result.accountId());

        verify(userDetails, times(1)).getId();
        verify(userDetails, times(1)).getAuthorities();
        verify(repository, times(1)).save(any(RefreshTokenEntity.class));
        verify(cacheService, times(1)).putToActiveAsTrue(any(RefreshToken.class));
        verifyNoMoreInteractions(repository, cacheService, userDetails);
    }

    @Test
    @DisplayName("UT: isActiveById() should delegate to repository")
    void isActiveById_shouldReturnRepositoryValue() {
        UUID id = UUID.randomUUID();
        when(repository.existsById(id)).thenReturn(true);

        boolean active = service.isActiveById(id);

        assertTrue(active);
        verify(repository, times(1)).existsById(id);
        verifyNoMoreInteractions(repository, cacheService);
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

        RefreshToken refreshToken = new RefreshToken(
                entity.getId(),
                entity.getAccountId(),
                Arrays.asList(entity.getAuthorities().split(",")),
                entity.getExpiresAt()
        );

        when(repository.findById(id)).thenReturn(Optional.of(entity));
        when(cacheService.put(refreshToken)).thenReturn(refreshToken);

        RefreshToken result = service.findById(id);

        assertNotNull(result);
        assertEquals(id, result.id());
        assertEquals(List.of("ROLE_USER", "ROLE_ADMIN"), result.authorities());

        verify(repository, times(1)).findById(id);
        verifyNoMoreInteractions(repository, cacheService);
    }

    @Test
    @DisplayName("UT: findById() when token not found should throw exception")
    void findById_whenNotFound_shouldThrow() {
        UUID id = UUID.randomUUID();
        when(repository.findById(id)).thenReturn(Optional.empty());
        doNothing().when(cacheService).putToActiveAsFalse(id);

        RefreshToken refreshToken = service.findById(id);
        assertEquals(refreshToken, null);

        verify(repository, times(1)).findById(id);
        verify(cacheService, times(1)).putToActiveAsFalse(any(UUID.class));
        verifyNoMoreInteractions(repository, cacheService);
    }

    @Test
    @DisplayName("UT: deleteById() should delete and put false into cache")
    void deleteById_shouldDeleteStatusAndPutFalseInCache() {
        UUID id = UUID.randomUUID();
        doNothing().when(cacheService).putToActiveAsFalse(id);

        service.deleteById(id);

        verify(repository, times(1)).deleteById(id);
        verify(cacheService, times(1)).putToActiveAsFalse(any(UUID.class));
        verifyNoMoreInteractions(repository, cacheService);
    }
}