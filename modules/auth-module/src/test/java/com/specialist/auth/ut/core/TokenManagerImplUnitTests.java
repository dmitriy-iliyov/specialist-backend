package com.specialist.auth.ut.core;

import com.specialist.auth.core.Token;
import com.specialist.auth.core.TokenManagerImpl;
import com.specialist.auth.core.TokenType;
import com.specialist.auth.core.web.oauth2.models.Provider;
import com.specialist.auth.domain.access_token.AccessTokenFactory;
import com.specialist.auth.domain.access_token.AccessTokenSerializer;
import com.specialist.auth.domain.access_token.models.AccessToken;
import com.specialist.auth.domain.account.models.AccountUserDetails;
import com.specialist.auth.domain.refresh_token.RefreshTokenService;
import com.specialist.auth.domain.refresh_token.models.RefreshToken;
import com.specialist.auth.domain.service_account.models.ServiceAccountUserDetails;
import com.specialist.auth.exceptions.RefreshTokenExpiredException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.time.Instant;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TokenManagerImplUnitTests {

    @Mock
    private RefreshTokenService refreshTokenService;

    @Mock
    private AccessTokenFactory accessTokenFactory;

    @Mock
    private AccessTokenSerializer accessTokenSerializer;

    private TokenManagerImpl tokenManager;

    private final Long ACCESS_TOKEN_TTL = 1800L;

    @BeforeEach
    void setUp() {
        tokenManager = new TokenManagerImpl(ACCESS_TOKEN_TTL, refreshTokenService, accessTokenFactory, accessTokenSerializer);
    }

    @Test
    @DisplayName("UT: generate(AccountUserDetails) with no authorities should generate tokens")
    void generateFromAccountUserDetails_withNoAuthorities_shouldGenerateTokens() {
        AccountUserDetails userDetails = new AccountUserDetails(
                UUID.randomUUID(), "test@test.com", "pass", Provider.LOCAL, Collections.emptyList(),
                false, null, null, false, null
        );
        RefreshToken refreshToken = new RefreshToken(UUID.randomUUID(), userDetails.getId(), Collections.emptyList(), Instant.now().plusSeconds(3600));
        AccessToken accessToken = new AccessToken(refreshToken.id(), userDetails.getId(), Collections.emptyList(), Instant.now(), Instant.now().plusSeconds(600));
        String rawAccessToken = "serialized.access.token";

        when(refreshTokenService.generateAndSave(userDetails)).thenReturn(refreshToken);
        when(accessTokenFactory.generate(refreshToken)).thenReturn(accessToken);
        when(accessTokenSerializer.serialize(accessToken)).thenReturn(rawAccessToken);

        Map<TokenType, Token> tokens = tokenManager.generate(userDetails);

        assertNotNull(tokens);
        assertEquals(2, tokens.size());
        verify(refreshTokenService).generateAndSave(userDetails);
        verify(accessTokenFactory).generate(refreshToken);
        verify(accessTokenSerializer).serialize(accessToken);
        verifyNoMoreInteractions(refreshTokenService, accessTokenFactory, accessTokenSerializer);
    }

    @Test
    @DisplayName("UT: generate(ServiceAccountUserDetails) should return only access token")
    public void generateFromServiceAccountUserDetails_shouldReturnOnlyAccessToken() {
        ServiceAccountUserDetails userDetails = new ServiceAccountUserDetails(
                UUID.randomUUID(), "service-account", List.of(new SimpleGrantedAuthority("ROLE_SERVICE"))
        );
        RefreshToken refreshToken = new RefreshToken(UUID.randomUUID(), userDetails.getId(), List.of("ROLE_SERVICE"), Instant.now().plusSeconds(3600));
        String rawAccessToken = "serialized-access-token";

        when(refreshTokenService.generateAndSave(userDetails)).thenReturn(refreshToken);
        when(accessTokenSerializer.serialize(any(AccessToken.class))).thenReturn(rawAccessToken);

        Token token = tokenManager.generate(userDetails);

        assertNotNull(token);
        assertEquals(TokenType.ACCESS, token.type());
        assertEquals(rawAccessToken, token.rawToken());
        verify(refreshTokenService).generateAndSave(userDetails);
        verify(accessTokenSerializer).serialize(any(AccessToken.class));
        verifyNoMoreInteractions(refreshTokenService, accessTokenFactory, accessTokenSerializer);
    }

    @Test
    @DisplayName("UT: refresh() when token is about to expire should throw RefreshTokenExpiredException and delete token")
    public void refresh_whenTokenIsAboutToExpire_shouldThrowAndDeactivate() {
        UUID tokenId = UUID.randomUUID();
        Instant almostExpired = Instant.now().plusSeconds(ACCESS_TOKEN_TTL - 10);
        RefreshToken refreshToken = new RefreshToken(tokenId, UUID.randomUUID(), List.of("ROLE_USER"), almostExpired);

        when(refreshTokenService.findById(tokenId)).thenReturn(refreshToken);

        assertThrows(RefreshTokenExpiredException.class, () -> tokenManager.refresh(tokenId));

        verify(refreshTokenService).findById(tokenId);
        verify(refreshTokenService).deleteById(tokenId);
        verifyNoMoreInteractions(refreshTokenService);
    }
}
