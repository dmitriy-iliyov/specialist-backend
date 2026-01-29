package com.specialist.auth.domain.refresh_token;

import com.specialist.auth.core.BaseUserDetails;
import com.specialist.auth.domain.refresh_token.models.RefreshToken;
import com.specialist.auth.domain.refresh_token.models.RefreshTokenEntity;
import com.specialist.auth.exceptions.RefreshTokenNotFoundByIdException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RefreshTokenServiceImpl implements RefreshTokenService {

    @Value("${api.refresh-token.ttl}")
    public Long TOKEN_TTL;
    private final RefreshTokenRepository repository;
    private final RefreshTokenCacheService cacheService;

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

    @Transactional(readOnly = true)
    @Override
    public boolean isActiveById(UUID id) {
        Boolean cachedIsActive = cacheService.isActiveById(id);
        if (cachedIsActive != null) {
            return cachedIsActive;
        }
        RefreshTokenEntity entity = repository.findById(id).orElseThrow(RefreshTokenNotFoundByIdException::new);
        if (!entity.getExpiresAt().isAfter(Instant.now())) {
            cacheService.putToActiveAsFalse(id);
            return false;
        }
        cacheService.putToActiveAsTrue(fromEntity(entity));
        return true;
    }

    @Transactional(readOnly = true)
    @Override
    public RefreshToken findById(UUID id) {
        RefreshTokenEntity entity = repository.findById(id).orElse(null);
        if (entity == null) {
            return null;
        }
        return fromEntity(entity);
    }

    @CacheEvict(value = "refresh-tokens:active", key = "#id")
    @Transactional
    @Override
    public void deleteById(UUID id) {
        repository.deleteById(id);
    }

    @Transactional
    @Override
    public void deleteAllByAccountId(UUID accountId) {
        List<UUID> refreshTokensIds = repository.findAllIdByAccountId(accountId);
        cacheService.evictAllByIdIn(new HashSet<>(refreshTokensIds));
        repository.deleteAllByAccountId(accountId);
    }

    @Transactional
    @Override
    public void deleteBatchByExpiredAtThreshold(Instant threshold, Integer batchSize) {
        repository.deleteBatchByExpiredAThreshold(threshold, batchSize);
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
