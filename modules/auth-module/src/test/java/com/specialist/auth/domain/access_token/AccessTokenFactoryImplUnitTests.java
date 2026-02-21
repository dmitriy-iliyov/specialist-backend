package com.specialist.auth.ut.domain.access_token;

import com.specialist.auth.domain.access_token.AccessTokenFactoryImpl;
import com.specialist.auth.domain.access_token.models.AccessToken;
import com.specialist.auth.domain.refresh_token.models.RefreshToken;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

import static org.junit.Assert.assertEquals;

@ExtendWith(MockitoExtension.class)
public class AccessTokenFactoryImplUnitTests {

    @InjectMocks
    AccessTokenFactoryImpl factory;

    @Test
    @DisplayName("UT: generate() AccessToken should return token")
    public void generate() {
        RefreshToken refreshToken = new RefreshToken(
                UUID.randomUUID(),
                UUID.randomUUID(),
                List.of("ROLE_USER"),
                Instant.now()
        );
        factory.TOKEN_TTL = 300L;

        AccessToken accessToken = factory.generate(refreshToken);

        assertEquals(refreshToken.id(), accessToken.id());
        assertEquals(refreshToken.accountId(), accessToken.accountId());
        assertEquals(refreshToken.authorities(), accessToken.authorities());
    }
}
