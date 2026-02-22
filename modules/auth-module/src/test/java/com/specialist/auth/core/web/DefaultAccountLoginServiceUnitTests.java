package com.specialist.auth.core.web;

import com.specialist.auth.core.web.oauth2.models.Provider;
import com.specialist.auth.domain.account.models.AccountUserDetails;
import com.specialist.auth.domain.account.services.AccountService;
import com.specialist.auth.exceptions.InvalidProviderException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DefaultAccountLoginServiceUnitTests {

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private AccountService accountService;

    @Mock
    private SessionCookieManager sessionCookieManager;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @InjectMocks
    private DefaultAccountLoginService service;

    @Test
    @DisplayName("UT: login() with valid local credentials should authenticate and create session")
    void login_validLocalCredentials_shouldAuthenticate() {
        LoginRequest loginRequest = new LoginRequest("test@test.com", "pass");
        Authentication authentication = mock(Authentication.class);
        AccountUserDetails userDetails = mock(AccountUserDetails.class);

        when(accountService.findProviderByEmail("test@test.com")).thenReturn(Provider.LOCAL);
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class))).thenReturn(authentication);
        when(authentication.getPrincipal()).thenReturn(userDetails);

        service.login(loginRequest, request, response);

        verify(accountService).findProviderByEmail("test@test.com");
        verify(authenticationManager).authenticate(any(UsernamePasswordAuthenticationToken.class));
        verify(sessionCookieManager).create(userDetails, request, response);
    }

    @Test
    @DisplayName("UT: login() with non-local provider should throw exception")
    void login_nonLocalProvider_shouldThrowException() {
        LoginRequest loginRequest = new LoginRequest("test@test.com", "pass");
        when(accountService.findProviderByEmail("test@test.com")).thenReturn(Provider.GOOGLE);

        assertThrows(InvalidProviderException.class, () -> service.login(loginRequest, request, response));

        verify(accountService).findProviderByEmail("test@test.com");
        verifyNoInteractions(authenticationManager, sessionCookieManager);
    }

    @Test
    @DisplayName("UT: login() with invalid credentials should throw exception")
    void login_invalidCredentials_shouldThrowException() {
        LoginRequest loginRequest = new LoginRequest("test@test.com", "wrong");
        when(accountService.findProviderByEmail("test@test.com")).thenReturn(Provider.LOCAL);
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenThrow(new BadCredentialsException("Bad credentials"));

        assertThrows(BadCredentialsException.class, () -> service.login(loginRequest, request, response));

        verify(authenticationManager).authenticate(any(UsernamePasswordAuthenticationToken.class));
        verifyNoInteractions(sessionCookieManager);
    }
}
