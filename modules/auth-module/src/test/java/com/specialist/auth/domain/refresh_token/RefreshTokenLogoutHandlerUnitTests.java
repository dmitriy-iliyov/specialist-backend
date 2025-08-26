package com.specialist.auth.domain.refresh_token;

import com.specialist.auth.core.TokenManager;
import com.specialist.auth.domain.access_token.models.AccessTokenUserDetails;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;

import java.util.UUID;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RefreshTokenLogoutHandlerUnitTests {

    @Mock
    private TokenManager tokenManager;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private Authentication authentication;

    @Mock
    private AccessTokenUserDetails userDetails;

    @InjectMocks
    private RefreshTokenLogoutHandler handler;

    @Test
    @DisplayName("UT: logout() should deactivate refresh token by user id")
    void logout_shouldDeactivateToken() {
        UUID tokenId = UUID.randomUUID();
        when(authentication.getPrincipal()).thenReturn(userDetails);
        when(userDetails.getId()).thenReturn(tokenId);

        handler.logout(request, response, authentication);

        verify(authentication, times(1)).getPrincipal();
        verify(userDetails, times(1)).getId();
        verify(tokenManager, times(1)).deactivate(tokenId);

        verifyNoMoreInteractions(authentication, userDetails, tokenManager);
        verifyNoInteractions(request, response);
    }
}
