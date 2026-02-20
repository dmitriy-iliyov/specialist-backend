package com.specialist.auth.ut.domain.access_token;

import com.specialist.auth.domain.access_token.AccessTokenSerializerImpl;
import com.specialist.auth.domain.access_token.models.AccessToken;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

class AccessTokenSerializerImplUnitTests {

    private AccessTokenSerializerImpl serializer;
    private final String secret = "01234567890123456789012345678901"; // 32 chars = 256 bits

    @BeforeEach
    void setUp() {
        serializer = new AccessTokenSerializerImpl();
        serializer.SECRET = secret;
    }

    @Test
    void serialize_shouldReturnJwtString() {
        UUID id = UUID.randomUUID();
        UUID subjectId = UUID.randomUUID();
        Instant now = Instant.now();
        Instant expiresAt = now.plusSeconds(3600);

        AccessToken token = new AccessToken(
                id,
                subjectId,
                List.of("ROLE_USER", "ROLE_ADMIN"),
                now,
                expiresAt
        );

        String jwt = serializer.serialize(token);

        assertNotNull(jwt);
        assertFalse(jwt.isBlank());
    }
}
