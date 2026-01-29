package com.specialist.auth.ut.domain.access_token;

import com.specialist.auth.domain.access_token.AccessTokenUserDetailsService;
import com.specialist.auth.domain.access_token.models.AccessToken;
import com.specialist.auth.domain.access_token.models.AccessTokenUserDetails;
import com.specialist.auth.domain.refresh_token.RefreshTokenService;
import com.specialist.auth.domain.role.Role;
import com.specialist.auth.exceptions.RefreshTokenExpiredException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AccessTokenUserDetailsServiceUnitTests {

    @Mock
    private RefreshTokenService refreshTokenService;

    @InjectMocks
    private AccessTokenUserDetailsService service;

    @Test
    @DisplayName("UT: loadUserDetails() when token is null should return null")
    void loadUserDetails_whenTokenNull_shouldReturnNull() {
        UserDetails result = service.loadUserDetails(null);
        assertNull(result);
        verifyNoInteractions(refreshTokenService);
    }

    @Test
    @DisplayName("UT: loadUserDetails() when principal is not AccessToken should return null")
    void loadUserDetails_whenPrincipalNotAccessToken_shouldReturnNull() {
        PreAuthenticatedAuthenticationToken token = mock(PreAuthenticatedAuthenticationToken.class);
        when(token.getPrincipal()).thenReturn(new Object());

        UserDetails userDetails = service.loadUserDetails(token);

        assertNull(userDetails);
        verifyNoInteractions(refreshTokenService);
    }

    @Test
    @DisplayName("UT: loadUserDetails() when refresh token inactive should throw RefreshTokenExpiredException")
    void loadUserDetails_whenRefreshTokenInactive_shouldThrow() {
        UUID tokenId = UUID.randomUUID();
        AccessToken accessToken = mock(AccessToken.class);
        when(accessToken.id()).thenReturn(tokenId);

        PreAuthenticatedAuthenticationToken token = mock(PreAuthenticatedAuthenticationToken.class);
        when(token.getPrincipal()).thenReturn(accessToken);

        when(refreshTokenService.isActiveById(tokenId)).thenReturn(false);

        assertThrows(RefreshTokenExpiredException.class, () -> service.loadUserDetails(token));

        verify(refreshTokenService).isActiveById(tokenId);
        verifyNoMoreInteractions(refreshTokenService);
    }

    @Test
    @DisplayName("UT: loadUserDetails() with no authorities should return user with no authorities")
    void loadUserDetails_withNoAuthorities_shouldReturnUserWithNoAuthorities() {
        UUID tokenId = UUID.randomUUID();
        UUID userId = UUID.randomUUID();
        AccessToken accessToken = mock(AccessToken.class);
        when(accessToken.id()).thenReturn(tokenId);
        when(accessToken.accountId()).thenReturn(userId);
        when(accessToken.authorities()).thenReturn(Collections.emptyList());

        PreAuthenticatedAuthenticationToken token = mock(PreAuthenticatedAuthenticationToken.class);
        when(token.getPrincipal()).thenReturn(accessToken);

        when(refreshTokenService.isActiveById(tokenId)).thenReturn(true);

        UserDetails userDetails = service.loadUserDetails(token);

        assertNotNull(userDetails);
        assertTrue(userDetails.getAuthorities().isEmpty());
        verify(refreshTokenService).isActiveById(tokenId);
        verifyNoMoreInteractions(refreshTokenService);
    }

    @Test
    @DisplayName("UT: loadUserDetails() when refresh token active should return AccessTokenUserDetails")
    void loadUserDetails_whenRefreshTokenActive_shouldReturnUserDetails() {
        UUID tokenId = UUID.randomUUID();
        UUID userId = UUID.randomUUID();
        List<String> authStrings = List.of(Role.ROLE_USER.name(), Role.ROLE_ADMIN.name());

        AccessToken accessToken = mock(AccessToken.class);
        when(accessToken.id()).thenReturn(tokenId);
        when(accessToken.accountId()).thenReturn(userId);
        when(accessToken.authorities()).thenReturn(authStrings);

        PreAuthenticatedAuthenticationToken token = mock(PreAuthenticatedAuthenticationToken.class);
        when(token.getPrincipal()).thenReturn(accessToken);

        when(refreshTokenService.isActiveById(tokenId)).thenReturn(true);

        UserDetails userDetails = service.loadUserDetails(token);

        assertNotNull(userDetails);
        assertTrue(userDetails instanceof AccessTokenUserDetails);

        AccessTokenUserDetails details = (AccessTokenUserDetails) userDetails;

        assertEquals(tokenId, details.getId());
        assertEquals(userId, details.getAccountId());
        assertEquals(authStrings.size(), details.getAuthorities().size());
        assertTrue(details.getAuthorities().stream()
                .allMatch(a -> a instanceof SimpleGrantedAuthority));

        verify(refreshTokenService).isActiveById(tokenId);
        verifyNoMoreInteractions(refreshTokenService);
    }
}
