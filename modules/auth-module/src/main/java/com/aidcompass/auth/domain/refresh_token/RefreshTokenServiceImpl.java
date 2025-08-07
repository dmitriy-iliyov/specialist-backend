package com.aidcompass.auth.domain.refresh_token;

import com.aidcompass.auth.domain.account.models.AccountUserDetails;
import com.aidcompass.auth.domain.refresh_token.models.RefreshToken;
import com.aidcompass.auth.domain.refresh_token.models.RefreshTokenEntity;
import com.aidcompass.auth.domain.refresh_token.models.RefreshTokenStatus;
import com.aidcompass.auth.domain.service_account.models.ServiceAccountUserDetails;
import com.aidcompass.auth.exceptions.RefreshTokenNotFoundByIdException;
import com.aidcompass.utils.UuidUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
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
    private final CacheManager cacheManager;

    @CachePut(value = "refresh-tokens", key = "#result.id()")
    @Transactional
    @Override
    public RefreshToken generateAndSave(AccountUserDetails userDetails) {
        UUID id = UuidUtils.generateV7();
        UUID accountId = userDetails.getId();
        List<String> authorities = userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList();
        Instant createdAt = Instant.now();
        Instant expiresAt = createdAt.plusSeconds(TOKEN_TTL);
        RefreshTokenEntity refreshTokenEntity = new RefreshTokenEntity(
                id, accountId, String.join(",", authorities), RefreshTokenStatus.ACTIVE, createdAt, expiresAt
        );
        repository.save(refreshTokenEntity);
        Cache cache = cacheManager.getCache("refresh-tokens:active");
        if (cache != null) {
            cache.put(id, Boolean.TRUE);
        }
        return new RefreshToken(
                refreshTokenEntity.getId(),
                accountId,
                authorities,
                refreshTokenEntity.getStatus(),
                refreshTokenEntity.getExpiresAt()
        );
    }

    @CachePut(value = "refresh-tokens", key = "#result.id()")
    @Transactional
    @Override
    public RefreshToken generateAndSave(ServiceAccountUserDetails userDetails) {
        UUID id = UuidUtils.generateV7();
        UUID accountId = userDetails.getId();
        List<String> authorities = userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList();
        Instant createdAt = Instant.now();
        Instant expiresAt = createdAt.plusSeconds(TOKEN_TTL);
        RefreshTokenEntity refreshTokenEntity = new RefreshTokenEntity(
                id, accountId, String.join(",", authorities), RefreshTokenStatus.ACTIVE, createdAt, expiresAt
        );
        repository.save(refreshTokenEntity);
        Cache cache = cacheManager.getCache("refresh-tokens:active");
        if (cache != null) {
            cache.put(id, Boolean.TRUE);
        }
        return new RefreshToken(
                refreshTokenEntity.getId(),
                accountId,
                authorities,
                refreshTokenEntity.getStatus(),
                refreshTokenEntity.getExpiresAt()
        );
    }

    @Cacheable(value = "refresh-tokens:active", key = "#id")
    @Transactional(readOnly = true)
    @Override
    public boolean isActiveById(UUID id) {
        return repository.existsByIdAndStatus(id, RefreshTokenStatus.ACTIVE);
    }

    @Cacheable(value = "refresh-tokens", key = "#id")
    @Transactional(readOnly = true)
    @Override
    public RefreshToken findById(UUID id) {
        RefreshTokenEntity entity = repository.findById(id).orElseThrow(RefreshTokenNotFoundByIdException::new);
        return new RefreshToken(
                entity.getId(),
                entity.getSubjectId(),
                Arrays.stream(entity.getAuthorities().split(",")).map(String::trim).toList(),
                entity.getStatus(),
                entity.getExpiresAt()
        );
    }

    @CacheEvict(value = "refresh-tokens", key = "#id")
    @Transactional
    @Override
    public void deactivateById(UUID id) {
        repository.updateStatusById(id, RefreshTokenStatus.DEACTIVATED);
        Cache cache = cacheManager.getCache("refresh-tokens:active");
        if (cache != null) {
            cache.put(id, Boolean.FALSE);
        }
    }

    @CacheEvict(value = "refresh-tokens", key = "#id")
    @Transactional
    @Override
    public void revokeById(UUID id) {
        repository.updateStatusById(id, RefreshTokenStatus.REVOKED);
        Cache cache = cacheManager.getCache("refresh-tokens:active");
        if (cache != null) {
            cache.put(id, Boolean.FALSE);
        }
    }
}
