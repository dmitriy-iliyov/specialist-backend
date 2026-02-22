package com.specialist.auth.core;

import com.specialist.auth.domain.access_token.AccessTokenFactory;
import com.specialist.auth.domain.access_token.AccessTokenSerializer;
import com.specialist.auth.domain.access_token.models.AccessToken;
import com.specialist.auth.domain.account.models.AccountUserDetails;
import com.specialist.auth.domain.refresh_token.RefreshTokenService;
import com.specialist.auth.domain.refresh_token.models.RefreshToken;
import com.specialist.auth.domain.service_account.models.ServiceAccountUserDetails;
import com.specialist.auth.exceptions.AccountIdNullException;
import com.specialist.auth.exceptions.RefreshTokenExpiredException;
import com.specialist.auth.exceptions.RefreshTokenIdNullException;
import com.specialist.auth.exceptions.UserDetailsNullException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;
import java.util.Collections;
import java.util.Map;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TokenManagerImplUnitTests {

    @Mock
    private RefreshTokenService refreshTokenService;

    @Mock
    private AccessTokenFactory accessTokenFactory;

    @Mock
    private AccessTokenSerializer accessTokenSerializer;

    @InjectMocks
    private TokenManagerImpl tokenManager;

    @Test
    @DisplayName("UT: generate(AccountUserDetails) with valid details should return tokens")
    void generate_accountUserDetails_shouldReturnTokens() {
        AccountUserDetails userDetails = mock(AccountUserDetails.class);
        RefreshToken refreshToken = new RefreshToken(UUID.randomUUID(), UUID.randomUUID(), Collections.emptyList(), Instant.now().plusSeconds(3600));
        AccessToken accessToken = new AccessToken(refreshToken.id(), refreshToken.accountId(), refreshToken.authorities(), Instant.now(), Instant.now().plusSeconds(900));

        when(refreshTokenService.generateAndSave(userDetails)).thenReturn(refreshToken);
        when(accessTokenFactory.generate(refreshToken)).thenReturn(accessToken);
        when(accessTokenSerializer.serialize(accessToken)).thenReturn("serialized_access_token");

        Map<TokenType, Token> tokens = tokenManager.generate(userDetails);

        assertNotNull(tokens);
        assertTrue(tokens.containsKey(TokenType.ACCESS));
        assertTrue(tokens.containsKey(TokenType.REFRESH));
        assertEquals("serialized_access_token", tokens.get(TokenType.ACCESS).rawToken());

        verify(refreshTokenService).generateAndSave(userDetails);
        verify(accessTokenFactory).generate(refreshToken);
        verify(accessTokenSerializer).serialize(accessToken);
    }

    @Test
    @DisplayName("UT: generate(AccountUserDetails) with null details should throw UserDetailsNullException")
    void generate_nullAccountUserDetails_shouldThrowException() {
        assertThrows(UserDetailsNullException.class, () -> tokenManager.generate((AccountUserDetails) null));
    }

    @Test
    @DisplayName("UT: generate(ServiceAccountUserDetails) with valid details should return access token")
    void generate_serviceAccountUserDetails_shouldReturnToken() {
        ServiceAccountUserDetails userDetails = mock(ServiceAccountUserDetails.class);
        RefreshToken refreshToken = new RefreshToken(UUID.randomUUID(), UUID.randomUUID(), Collections.emptyList(), Instant.now().plusSeconds(3600));

        when(refreshTokenService.generateAndSave(userDetails)).thenReturn(refreshToken);
        when(accessTokenSerializer.serialize(any(AccessToken.class))).thenReturn("serialized_access_token");

        Token token = tokenManager.generate(userDetails);

        assertNotNull(token);
        assertEquals(TokenType.ACCESS, token.type());
        assertEquals("serialized_access_token", token.rawToken());

        verify(refreshTokenService).generateAndSave(userDetails);
        verify(accessTokenSerializer).serialize(any(AccessToken.class));
    }

    @Test
    @DisplayName("UT: generate(ServiceAccountUserDetails) with null details should throw UserDetailsNullException")
    void generate_nullServiceAccountUserDetails_shouldThrowException() {
        assertThrows(UserDetailsNullException.class, () -> tokenManager.generate((ServiceAccountUserDetails) null));
    }

    @Test
    @DisplayName("UT: refresh() with null id should throw RefreshTokenIdNullException")
    void refresh_nullId_shouldThrowException() {
        assertThrows(RefreshTokenIdNullException.class, () -> tokenManager.refresh(null));
    }
    
    @Test
    @DisplayName("UT: refresh() with non-existent token should throw RefreshTokenExpiredException")
    void refresh_nonExistentToken_shouldThrowException() {
        UUID refreshTokenId = UUID.randomUUID();
        when(refreshTokenService.findById(refreshTokenId)).thenReturn(null);
        assertThrows(RefreshTokenExpiredException.class, () -> tokenManager.refresh(refreshTokenId));
        verify(refreshTokenService).findById(refreshTokenId);
    }

    @Test
    @DisplayName("UT: deactivate() with valid id should delete token")
    void deactivate_validId_shouldDeleteToken() {
        UUID refreshTokenId = UUID.randomUUID();
        tokenManager.deactivate(refreshTokenId);
        verify(refreshTokenService).deleteById(refreshTokenId);
    }

    @Test
    @DisplayName("UT: deactivate() with null id should throw RefreshTokenIdNullException")
    void deactivate_nullId_shouldThrowException() {
        assertThrows(RefreshTokenIdNullException.class, () -> tokenManager.deactivate(null));
    }

    @Test
    @DisplayName("UT: deactivateAll() with valid id should delete all tokens")
    void deactivateAll_validId_shouldDeleteAllTokens() {
        UUID accountId = UUID.randomUUID();
        tokenManager.deactivateAll(accountId);
        verify(refreshTokenService).deleteAllByAccountId(accountId);
    }

    @Test
    @DisplayName("UT: deactivateAll() with null id should throw AccountIdNullException")
    void deactivateAll_nullId_shouldThrowException() {
        assertThrows(AccountIdNullException.class, () -> tokenManager.deactivateAll(null));
    }
    
    @Test
    @DisplayName("UT: revoke() with valid id should delete token")
    void revoke_validId_shouldDeleteToken() {
        UUID refreshTokenId = UUID.randomUUID();
        tokenManager.revoke(refreshTokenId);
        verify(refreshTokenService).deleteById(refreshTokenId);
    }

    @Test
    @DisplayName("UT: revoke() with null id should throw RefreshTokenIdNullException")
    void revoke_nullId_shouldThrowException() {
        assertThrows(RefreshTokenIdNullException.class, () -> tokenManager.revoke(null));
    }

    @Test
    @DisplayName("UT: revokeAll() with valid id should delete all tokens")
    void revokeAll_validId_shouldDeleteAllTokens() {
        UUID accountId = UUID.randomUUID();
        tokenManager.revokeAll(accountId);
        verify(refreshTokenService).deleteAllByAccountId(accountId);
    }

    @Test
    @DisplayName("UT: revokeAll() with null id should throw AccountIdNullException")
    void revokeAll_nullId_shouldThrowException() {
        assertThrows(AccountIdNullException.class, () -> tokenManager.revokeAll(null));
    }
}
