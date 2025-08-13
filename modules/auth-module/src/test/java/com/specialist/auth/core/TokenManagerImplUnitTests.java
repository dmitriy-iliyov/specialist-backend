package com.specialist.auth.core;

import com.specialist.auth.core.models.Token;
import com.specialist.auth.core.models.TokenType;
import com.specialist.auth.domain.access_token.AccessTokenFactory;
import com.specialist.auth.domain.access_token.AccessTokenSerializer;
import com.specialist.auth.domain.access_token.models.AccessToken;
import com.specialist.auth.domain.account.models.AccountUserDetails;
import com.specialist.auth.domain.auth_provider.Provider;
import com.specialist.auth.domain.refresh_token.RefreshTokenService;
import com.specialist.auth.domain.refresh_token.models.RefreshToken;
import com.specialist.auth.domain.refresh_token.models.RefreshTokenStatus;
import com.specialist.auth.domain.service_account.models.ServiceAccountUserDetails;
import com.specialist.auth.exceptions.RefreshTokenExpiredException;
import com.specialist.auth.exceptions.RefreshTokenIdNullException;
import com.specialist.auth.exceptions.UserDetailsNullException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.Base64;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TokenManagerImplUnitTests {

    @Mock
    RefreshTokenService refreshTokenService;

    @Mock
    AccessTokenFactory accessTokenFactory;

    @Mock
    AccessTokenSerializer accessTokenSerializer;

    @InjectMocks
    TokenManagerImpl tokenManager;

    @Test
    @DisplayName("UT: generate(AccountUserDetails.class) when userDetails non null should return map of tokens")
    public void generateFromAccountUserDetails_shouldReturnMapOfTokens() {
        AccountUserDetails userDetails = new AccountUserDetails(
                UUID.randomUUID(),
                "test@gmail.com",
                "securepassword",
                Provider.LOCAL,
                List.of(new SimpleGrantedAuthority("ROLE_USER")),
                false,
                true
        );

        RefreshToken refreshToken = new RefreshToken(
                UUID.randomUUID(),
                userDetails.getId(),
                List.of("ROLE_USER"),
                RefreshTokenStatus.ACTIVE,
                Instant.now().plusSeconds(100L)
        );

        AccessToken accessToken = new AccessToken(
                refreshToken.id(),
                userDetails.getId(),
                List.of("ROLE_USER"),
                Instant.now(),
                Instant.now().plusSeconds(100L)
        );

        String rawAccessToken = accessToken.toString();

        when(refreshTokenService.generateAndSave(userDetails)).thenReturn(refreshToken);
        when(accessTokenFactory.generate(refreshToken)).thenReturn(accessToken);
        when(accessTokenSerializer.serialize(accessToken)).thenReturn(rawAccessToken);

        Map<TokenType, Token> tokens = tokenManager.generate(userDetails);

        assertEquals(tokens.get(TokenType.ACCESS).rawToken(), rawAccessToken);
        assertEquals(
                tokens.get(TokenType.REFRESH).rawToken(),
                Base64.getUrlEncoder()
                        .withoutPadding()
                        .encodeToString(refreshToken.id().toString().getBytes(StandardCharsets.UTF_8))
        );
        verify(refreshTokenService, times(1)).generateAndSave(userDetails);
        verify(accessTokenFactory, times(1)).generate(refreshToken);
        verify(accessTokenSerializer, times(1)).serialize(accessToken);
        verifyNoMoreInteractions(refreshTokenService, accessTokenFactory, accessTokenSerializer);
    }

    @Test
    @DisplayName("UT: generate(AccountUserDetails.class) when userDetails null should throw UserDetailsNullException.class")
    public void generateFromAccountUserDetails_whenUserDetailsNull_shouldThrowUserDetailsNullException() {
        AccountUserDetails userDetails = null;

        assertThrows(UserDetailsNullException.class, () -> tokenManager.generate(userDetails));

        verify(refreshTokenService, times(0)).generateAndSave(any());
        verify(accessTokenFactory, times(0)).generate(any());
        verify(accessTokenSerializer, times(0)).serialize(any());
        verifyNoMoreInteractions(refreshTokenService, accessTokenFactory, accessTokenSerializer);
    }

    @Test
    @DisplayName("UT: generate(ServiceAccountUserDetails.class) when userDetails non null should return map with only access token")
    public void generateFromServiceAccountUserDetails_shouldReturnMapWithAccessToken() {
        ServiceAccountUserDetails userDetails = new ServiceAccountUserDetails(
                UUID.randomUUID(),
                "service-account",
                List.of(new SimpleGrantedAuthority("ROLE_SERVICE"))
        );

        RefreshToken refreshToken = new RefreshToken(
                UUID.randomUUID(),
                userDetails.getId(),
                List.of("ROLE_SERVICE"),
                RefreshTokenStatus.ACTIVE,
                Instant.now().plusSeconds(300L)
        );

        AccessToken accessToken = new AccessToken(
                refreshToken.id(),
                refreshToken.subjectId(),
                refreshToken.authorities(),
                Instant.now(),
                refreshToken.expiresAt()
        );

        String rawAccessToken = "serialized-access-token";

        when(refreshTokenService.generateAndSave(userDetails)).thenReturn(refreshToken);
        when(accessTokenSerializer.serialize(any(AccessToken.class))).thenReturn(rawAccessToken);

        Map<String, String> result = tokenManager.generate(userDetails);

        assertEquals(rawAccessToken, result.get("access_token"));
        verify(refreshTokenService, times(1)).generateAndSave(userDetails);
        verify(accessTokenSerializer, times(1)).serialize(any(AccessToken.class));
        verifyNoMoreInteractions(refreshTokenService, accessTokenFactory, accessTokenSerializer);
    }

    @Test
    @DisplayName("UT: generate(ServiceAccountUserDetails.class) when userDetails null should throw UserDetailsNullException.class")
    public void generateFromServiceAccountUserDetails_whenUserDetailsNull_shouldThrowUserDetailsNullException() {
        ServiceAccountUserDetails userDetails = null;

        assertThrows(UserDetailsNullException.class, () -> tokenManager.generate(userDetails));

        verify(refreshTokenService, times(0)).generateAndSave(any());
        verify(accessTokenSerializer, times(0)).serialize(any());
        verifyNoMoreInteractions(refreshTokenService, accessTokenFactory, accessTokenSerializer);
    }

    @Test
    @DisplayName("UT: refresh() when tokenId null should throw RefreshTokenIdNullException.class")
    public void refresh_whenTokenIdNull_shouldThrowRefreshTokenIdNullException() {
        assertThrows(RefreshTokenIdNullException.class, () -> tokenManager.refresh(null));
        verifyNoMoreInteractions(refreshTokenService, accessTokenFactory, accessTokenSerializer);
    }

    @Test
    @DisplayName("UT: refresh() when refresh token ACTIVE and not near expiry should return new access token")
    public void refresh_whenTokenActiveAndNotNearExpiry_shouldReturnAccessToken() {
        UUID tokenId = UUID.randomUUID();
        RefreshToken refreshToken = new RefreshToken(
                tokenId,
                UUID.randomUUID(),
                List.of("ROLE_USER"),
                RefreshTokenStatus.ACTIVE,
                Instant.now().plusSeconds(500L)
        );
        AccessToken accessToken = new AccessToken(
                tokenId,
                refreshToken.subjectId(),
                refreshToken.authorities(),
                Instant.now(),
                Instant.now().plusSeconds(500L)
        );

        String rawAccessToken = "serialized-token";

        when(refreshTokenService.findById(tokenId)).thenReturn(refreshToken);
        when(accessTokenFactory.generate(refreshToken)).thenReturn(accessToken);
        when(accessTokenSerializer.serialize(accessToken)).thenReturn(rawAccessToken);

        Token result = tokenManager.refresh(tokenId);

        assertEquals(TokenType.ACCESS, result.type());
        assertEquals(rawAccessToken, result.rawToken());
        assertEquals(accessToken.expiresAt(), result.expiresAt());

        verify(refreshTokenService, times(1)).findById(tokenId);
        verify(accessTokenFactory, times(1)).generate(refreshToken);
        verify(accessTokenSerializer, times(1)).serialize(accessToken);
    }

    @Test
    @DisplayName("UT: refresh() when refresh token ACTIVE but expires soon should throw RefreshTokenExpiredException.class")
    public void refresh_whenTokenActiveButNearExpiry_shouldThrowRefreshTokenExpiredException() {
        UUID tokenId = UUID.randomUUID();
        RefreshToken refreshToken = new RefreshToken(
                tokenId,
                UUID.randomUUID(),
                List.of("ROLE_USER"),
                RefreshTokenStatus.ACTIVE,
                Instant.now().plusSeconds(60L)
        );

        when(refreshTokenService.findById(tokenId)).thenReturn(refreshToken);

        assertThrows(RefreshTokenExpiredException.class, () -> tokenManager.refresh(tokenId));
        verify(refreshTokenService, times(1)).deactivateById(tokenId);
    }

    @Test
    @DisplayName("UT: refresh() when refresh token has expired but ACTIVE for some reason should throw RefreshTokenExpiredException.class")
    public void refresh_whenTokenActiveButHasExpired_shouldThrowRefreshTokenExpiredException() {
        UUID tokenId = UUID.randomUUID();
        RefreshToken refreshToken = new RefreshToken(
                tokenId,
                UUID.randomUUID(),
                List.of("ROLE_USER"),
                RefreshTokenStatus.ACTIVE,
                Instant.now().plusSeconds(-60L)
        );

        when(refreshTokenService.findById(tokenId)).thenReturn(refreshToken);

        assertThrows(RefreshTokenExpiredException.class, () -> tokenManager.refresh(tokenId));
        verify(refreshTokenService, times(1)).deactivateById(tokenId);
    }

    @Test
    @DisplayName("UT: refresh() when refresh token not ACTIVE should throw RefreshTokenExpiredException.class")
    public void refresh_whenTokenNotActive_shouldThrowRefreshTokenExpiredException() {
        UUID tokenId = UUID.randomUUID();
        RefreshToken refreshToken = new RefreshToken(
                tokenId,
                UUID.randomUUID(),
                List.of("ROLE_USER"),
                RefreshTokenStatus.DEACTIVATED,
                Instant.now().plusSeconds(300L)
        );

        when(refreshTokenService.findById(tokenId)).thenReturn(refreshToken);

        assertThrows(RefreshTokenExpiredException.class, () -> tokenManager.refresh(tokenId));
    }
}
