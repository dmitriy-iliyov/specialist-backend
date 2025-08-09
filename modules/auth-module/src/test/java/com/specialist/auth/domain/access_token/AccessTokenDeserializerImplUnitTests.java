package com.specialist.auth.domain.access_token;

import com.specialist.auth.domain.access_token.models.AccessToken;
import com.specialist.auth.exceptions.AccessTokenExpiredException;
import com.specialist.auth.exceptions.InvalidJwtSignatureException;
import com.specialist.auth.exceptions.JwtParseException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import javax.crypto.SecretKey;
import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class AccessTokenDeserializerImplUnitTests {

    private AccessTokenDeserializerImpl deserializer;
    private String secret;
    private SecretKey key;

    @BeforeEach
    void setUp() {
        deserializer = new AccessTokenDeserializerImpl();
        secret = "12345678901234567890123456789012";
        deserializer.SECRET = secret;
        key = Keys.hmacShaKeyFor(secret.getBytes());
    }

    @Test
    @DisplayName("UT: should throw InvalidJwtSignatureException on invalid signature")
    void shouldThrowInvalidJwtSignatureException() {
        String badToken = "invalid.jwt.token";

        assertThrows(InvalidJwtSignatureException.class,
                () -> deserializer.deserialize(badToken));
    }

    @Test
    @DisplayName("UT: should throw JwtParseException on invalid payload structure")
    void shouldThrowJwtParseException() {
        String token = Jwts.builder()
                .id("not-a-uuid")
                .subject("also-not-uuid")
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();

        assertThrows(JwtParseException.class,
                () -> deserializer.deserialize(token));
    }

    @Test
    @DisplayName("UT: should throw AccessTokenExpiredException if expired")
    void shouldThrowAccessTokenExpiredException() {
        String token = Jwts.builder()
                .id(UUID.randomUUID().toString())
                .subject(UUID.randomUUID().toString())
                .claim("authorities", List.of("ROLE_USER"))
                .issuedAt(Date.from(Instant.now()))
                .expiration(Date.from(Instant.now().minusSeconds(60)))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();

        assertThrows(AccessTokenExpiredException.class,
                () -> deserializer.deserialize(token));
    }

    @Test
    @DisplayName("UT: should return AccessToken if valid")
    void shouldReturnAccessToken() {
        UUID id = UUID.randomUUID();
        UUID subjectId = UUID.randomUUID();
        List<String> authorities = List.of("ROLE_USER");

        String token = Jwts.builder()
                .id(id.toString())
                .subject(subjectId.toString())
                .claim("authorities", authorities)
                .issuedAt(Date.from(Instant.now()))
                .expiration(Date.from(Instant.now().plusSeconds(3600)))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();

        AccessToken result = deserializer.deserialize(token);

        assertNotNull(result);
        assertEquals(id, result.id());
        assertEquals(subjectId, result.subjectId());
        assertEquals(authorities, result.authorities());
        assertNotNull(result.createdAt());
        assertNotNull(result.expiresAt());
    }
}
