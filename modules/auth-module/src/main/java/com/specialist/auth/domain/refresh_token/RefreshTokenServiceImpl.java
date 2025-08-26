package com.specialist.auth.domain.refresh_token;

import com.specialist.auth.core.models.BaseUserDetails;
import com.specialist.auth.domain.refresh_token.models.RefreshToken;
import com.specialist.auth.domain.refresh_token.models.RefreshTokenEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.Cache;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RefreshTokenServiceImpl implements RefreshTokenService {

    @Value("${api.refresh-token.ttl}")
    public Long TOKEN_TTL;
    private final RefreshTokenRepository repository;
    private final RefreshTokenCacheService cacheService;

    @CachePut(value = "refresh-tokens", key = "#result.id()")
    @Transactional
    @Override
    public RefreshToken generateAndSave(BaseUserDetails userDetails) {
        UUID accountId = userDetails.getId();
        List<String> authorities = userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList();
        Instant createdAt = Instant.now();
        Instant expiresAt = createdAt.plusSeconds(TOKEN_TTL);
        RefreshTokenEntity refreshTokenEntity = new RefreshTokenEntity(
                accountId, String.join(",", authorities), createdAt, expiresAt
        );
        return cacheService.putToActiveAsTrue(fromEntity(repository.save(refreshTokenEntity)));
    }

    @Cacheable(value = "refresh-tokens:active", key = "#id")
    @Transactional(readOnly = true)
    @Override
    public boolean isActiveById(UUID id) {
        return repository.existsById(id);
    }

    @Cacheable(value = "refresh-tokens", key = "#id")
    @Transactional(readOnly = true)
    @Override
    public RefreshToken findById(UUID id) {
        RefreshTokenEntity entity = repository.findById(id).orElse(null);
        if (entity == null) {
            cacheService.putToActiveAsFalse(id);
            return null;
        }
        return cacheService.put(fromEntity(entity));
    }

    @CacheEvict(value = "refresh-tokens", key = "#id")
    @Transactional
    @Override
    public void deleteById(UUID id) {
        repository.deleteById(id);
        cacheService.putToActiveAsFalse(id);
    }

    @Transactional
    @Override
    public void deleteAllByAccountId(UUID accountId) {
        List<UUID> refreshTokensIds = repository.findAllIdByAccountId(accountId);
        for (UUID id: refreshTokensIds) {
            cacheService.putToActiveAsFalse(id);
            cacheService.evictById(id);
        }
        repository.deleteAllByAccountId(accountId);
    }

    private RefreshToken fromEntity(RefreshTokenEntity entity) {
        return new RefreshToken(
                entity.getId(),
                entity.getAccountId(),
                Arrays.stream(entity.getAuthorities().split(",")).map(String::trim).toList(),
                entity.getExpiresAt()
        );
    }
}
