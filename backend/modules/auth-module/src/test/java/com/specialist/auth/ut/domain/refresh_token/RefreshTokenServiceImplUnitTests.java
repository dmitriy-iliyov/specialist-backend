package com.specialist.auth.ut.domain.refresh_token;

import com.specialist.auth.core.BaseUserDetails;
import com.specialist.auth.domain.refresh_token.RefreshTokenCacheService;
import com.specialist.auth.domain.refresh_token.RefreshTokenRepository;
import com.specialist.auth.domain.refresh_token.RefreshTokenServiceImpl;
import com.specialist.auth.domain.refresh_token.models.RefreshToken;
import com.specialist.auth.domain.refresh_token.models.RefreshTokenEntity;
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
        Collection<? extends GrantedAuthority> authorities =
                List.of(new SimpleGrantedAuthority("ROLE_USER"));
        RefreshToken refreshToken = new RefreshToken(UUID.randomUUID(), userId, List.of("ROLE_USER"), Instant.now());
        when(userDetails.getId()).thenReturn(userId);
        when(userDetails.getAuthorities()).thenReturn((Collection) authorities);
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
        Collection<? extends GrantedAuthority> authorities =
                List.of(new SimpleGrantedAuthority("ROLE_USER"));
        RefreshToken refreshToken = new RefreshToken(UUID.randomUUID(), userId, List.of("ROLE_USER"), Instant.now());
        when(userDetails.getId()).thenReturn(userId);
        when(userDetails.getAuthorities()).thenReturn((Collection) authorities);
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

        when(cacheService.isActiveById(id)).thenReturn(null);
        when(repository.findById(id)).thenReturn(
                Optional.of(new RefreshTokenEntity(
                        id,
                        UUID.randomUUID(),
                        "ROLE_USER",
                        Instant.now(),
                        Instant.now().plusSeconds(3600))
                )
        );

        boolean active = service.isActiveById(id);

        assertTrue(active);
        verify(cacheService, times(1)).isActiveById(id);
        verify(repository, times(1)).findById(id);
        verify(cacheService, times(1)).putToActiveAsTrue(any(RefreshToken.class));
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

        when(repository.findById(id)).thenReturn(Optional.of(entity));

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

        RefreshToken refreshToken = service.findById(id);
        assertNull(refreshToken);

        verify(repository, times(1)).findById(id);
        verifyNoMoreInteractions(repository, cacheService);
    }

    @Test
    @DisplayName("UT: deleteById() should delete and put false into cache")
    void deleteById_shouldDeleteStatusAndPutFalseInCache() {
        UUID id = UUID.randomUUID();

        service.deleteById(id);

        verify(repository, times(1)).deleteById(id);
        verifyNoMoreInteractions(repository, cacheService);
    }

    @Test
    @DisplayName("UT: deleteAllByAccountId() should evict cache and delete from repository")
    void deleteAllByAccountId_shouldEvictCacheAndDeleteFromRepo() {
        UUID accountId = UUID.randomUUID();
        List<UUID> ids = List.of(UUID.randomUUID(), UUID.randomUUID());

        when(repository.findAllIdByAccountId(accountId)).thenReturn(ids);

        service.deleteAllByAccountId(accountId);

        verify(repository, times(1)).findAllIdByAccountId(accountId);
        verify(cacheService, times(1)).evictAllByIdIn(new HashSet<>(ids));
        verify(repository, times(1)).deleteAllByAccountId(accountId);
        verifyNoMoreInteractions(repository, cacheService);
    }

    @Test
    @DisplayName("UT: deleteBatchByExpiredAtThreshold() should delegate to repository")
    void deleteBatchByExpiredAtThreshold_shouldDelegateToRepo() {
        Instant threshold = Instant.now();
        Integer batchSize = 100;

        service.deleteBatchByExpiredAtThreshold(threshold, batchSize);

        verify(repository, times(1)).deleteBatchByExpiredAThreshold(threshold, batchSize);
        verifyNoMoreInteractions(repository, cacheService);
    }
}