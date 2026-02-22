package com.specialist.auth.core.web.handlers;

import com.specialist.auth.core.web.SessionCookieManager;
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

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SessionCookieLogoutHandlerUnitTests {

    @Mock
    private SessionCookieManager sessionCookieManager;

    @InjectMocks
    private SessionCookieLogoutHandler logoutHandler;

    @Test
    @DisplayName("UT: logout() should terminate session")
    void logout_shouldTerminateSession() {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        Authentication authentication = mock(Authentication.class);
        AccessTokenUserDetails userDetails = mock(AccessTokenUserDetails.class);
        UUID refreshTokenId = UUID.randomUUID();

        when(authentication.getPrincipal()).thenReturn(userDetails);
        when(userDetails.getId()).thenReturn(refreshTokenId);

        logoutHandler.logout(request, response, authentication);

        verify(sessionCookieManager).terminate(refreshTokenId, response);
    }
}
