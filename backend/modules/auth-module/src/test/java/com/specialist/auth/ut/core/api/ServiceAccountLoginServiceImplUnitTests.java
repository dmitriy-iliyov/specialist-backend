package com.specialist.auth.ut.core.api;

import com.specialist.auth.core.Token;
import com.specialist.auth.core.TokenManager;
import com.specialist.auth.core.TokenType;
import com.specialist.auth.core.api.ServiceAccountLoginServiceImpl;
import com.specialist.auth.core.api.ServiceLoginRequest;
import com.specialist.auth.domain.service_account.models.ServiceAccountUserDetails;
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

import java.util.Map;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ServiceAccountLoginServiceImplUnitTests {

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private TokenManager tokenManager;

    @InjectMocks
    private ServiceAccountLoginServiceImpl service;

    @Test
    @DisplayName("UT: login() with valid credentials should return access token")
    void login_validCredentials_shouldReturnAccessToken() {
        ServiceLoginRequest request = new ServiceLoginRequest(UUID.randomUUID().toString(), "secret");
        Authentication authentication = mock(Authentication.class);
        ServiceAccountUserDetails userDetails = mock(ServiceAccountUserDetails.class);
        Token token = new Token(TokenType.ACCESS, "token_string", null);

        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class))).thenReturn(authentication);
        when(authentication.getPrincipal()).thenReturn(userDetails);
        when(tokenManager.generate(userDetails)).thenReturn(token);

        Map<String, String> result = service.login(request);

        assertEquals("token_string", result.get("access_token"));
        verify(authenticationManager).authenticate(any(UsernamePasswordAuthenticationToken.class));
        verify(tokenManager).generate(userDetails);
    }

    @Test
    @DisplayName("UT: login() with invalid credentials should throw exception")
    void login_invalidCredentials_shouldThrowException() {
        ServiceLoginRequest request = new ServiceLoginRequest(UUID.randomUUID().toString(), "wrong");
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenThrow(new BadCredentialsException("Bad credentials"));

        assertThrows(BadCredentialsException.class, () -> service.login(request));
        
        verify(authenticationManager).authenticate(any(UsernamePasswordAuthenticationToken.class));
        verifyNoInteractions(tokenManager);
    }
}
