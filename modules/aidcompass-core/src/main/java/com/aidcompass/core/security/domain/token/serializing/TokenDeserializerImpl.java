package com.aidcompass.core.security.domain.token.serializing;

import com.aidcompass.core.security.domain.token.models.Token;
import com.aidcompass.core.security.domain.token.models.TokenType;
import com.aidcompass.core.security.exceptions.illegal_input.TokenExpired;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwt;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.crypto.SecretKey;
import java.time.Instant;
import java.util.List;
import java.util.UUID;


@Data
@RequiredArgsConstructor
@Slf4j
public class TokenDeserializerImpl implements TokenDeserializer {

    private final String SECRET;


    @Override
    public Token deserialize(String inputJwt) {

        SecretKey key = Keys.hmacShaKeyFor(SECRET.getBytes());
        Jwt<?, ?> jwt;
        try {
             jwt = Jwts.parser()
                     .verifyWith(key)
                     .build()
                     .parseSignedClaims(inputJwt);
        } catch (Exception e){
            log.error("Exception when check jwt signature: {}", e.getMessage());
            return null;
        }
        try{
            Claims claims = (Claims) jwt.getPayload();
            UUID id = UUID.fromString(claims.getId());
            UUID subjectId = UUID.fromString(claims.getSubject());
            List<String> authorities = claims.get("authorities", List.class);
            TokenType type = TokenType.valueOf(claims.get("type", String.class));
            Instant issuedAt = claims.getIssuedAt().toInstant();
            Instant expiresAt = claims.getExpiration().toInstant();
            if (expiresAt != null) {
                if (Instant.now().isBefore(expiresAt)) {
                    return new Token(id, subjectId, type, authorities, issuedAt, expiresAt);
                }
                throw new TokenExpired();
            }
            throw new TokenExpired();
        } catch (Exception e){
            log.error("Exception when get claims and deserialize to Token: {}", e.getMessage());
            return null;
        }
    }
}