package com.specialist.auth.domain.access_token;

import com.specialist.auth.domain.access_token.models.AccessToken;
import com.specialist.auth.exceptions.AccessTokenExpiredException;
import com.specialist.auth.exceptions.InvalidJwtSignatureException;
import com.specialist.auth.exceptions.JwtParseException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwt;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
@Slf4j
public class AccessTokenDeserializerImpl implements AccessTokenDeserializer {

    @Value("${api.access-token.secret}")
    public String SECRET;

    @Override
    public AccessToken deserialize(String rawToken) {
        SecretKey key = Keys.hmacShaKeyFor(SECRET.getBytes());
        Jwt<?, ?> jwt;
        try {
            jwt = Jwts.parser()
                    .verifyWith(key)
                    .build()
                    .parseSignedClaims(rawToken);
        } catch (Exception e) {
            log.error("Exception when check jwt signature: {}", e.getMessage());
            throw new InvalidJwtSignatureException(e);
        }
        try {
            Claims claims = (Claims) jwt.getPayload();
            UUID id = UUID.fromString(claims.getId());
            UUID subjectId = UUID.fromString(claims.getSubject());
            List<String> authorities = claims.get("authorities", List.class);
            Instant createdAt = claims.getIssuedAt().toInstant();
            Instant expiresAt = claims.getExpiration().toInstant();
            if (expiresAt != null && expiresAt.isBefore(Instant.now())) {
                throw new AccessTokenExpiredException();
            }
            return new AccessToken(id, subjectId, authorities, createdAt, expiresAt);
        } catch (Exception e) {
            log.error("Exception when deserialize jwt: {}", e.getMessage());
            throw new JwtParseException(e);
        }
    }
}