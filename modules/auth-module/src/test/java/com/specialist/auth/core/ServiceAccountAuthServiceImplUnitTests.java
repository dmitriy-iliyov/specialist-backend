package com.specialist.auth.core;

import com.specialist.auth.core.models.ServiceLoginRequest;
import com.specialist.auth.domain.service_account.models.ServiceAccountUserDetails;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ServiceAccountAuthServiceImplUnitTests {

    @Mock
    AuthenticationManager authenticationManager;

    @Mock
    TokenManager tokenManager;

    @Mock
    HttpServletRequest httpServletRequest;

    @Mock
    Authentication authentication;

    @InjectMocks
    ServiceAccountAuthServiceImpl service;

    @Test
    @DisplayName("UT: login() when authentication successful should return tokens map")
    public void login_whenAuthenticationSuccessful_shouldReturnTokens() {
        ServiceLoginRequest requestDto = new ServiceLoginRequest("client-id", "client-secret");
        ServiceAccountUserDetails userDetails = new ServiceAccountUserDetails(
                UUID.randomUUID(),
                "service-account",
                List.of(new SimpleGrantedAuthority("ROLE_SERVICE"))
        );

        Map<String, String> expectedTokens = Map.of("access_token", "access-token-value");

        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenReturn(authentication);
        when(authentication.getPrincipal()).thenReturn(userDetails);
        when(tokenManager.generate(userDetails)).thenReturn(expectedTokens);

        Map<String, String> result = service.login(requestDto, httpServletRequest);

        assertEquals(expectedTokens, result);
        assertEquals(authentication, SecurityContextHolder.getContext().getAuthentication());

        verify(authenticationManager, times(1))
                .authenticate(argThat(token ->
                        token.getPrincipal().equals("client-id") &&
                                token.getCredentials().equals("client-secret")
                ));
        verify(tokenManager, times(1)).generate(userDetails);
        verifyNoMoreInteractions(authenticationManager, tokenManager);
    }

    @Test
    @DisplayName("UT: login() when authentication fails should throw AuthenticationException and clear context")
    public void login_whenAuthenticationFails_shouldThrowAuthenticationException() {
        ServiceLoginRequest requestDto = new ServiceLoginRequest("client-id", "wrong-secret");

        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenThrow(new AuthenticationException("Bad credentials") {});

        assertThrows(AuthenticationException.class, () -> service.login(requestDto, httpServletRequest));

        assertEquals(null, SecurityContextHolder.getContext().getAuthentication());

        verify(authenticationManager, times(1))
                .authenticate(argThat(token ->
                        token.getPrincipal().equals("client-id") &&
                                token.getCredentials().equals("wrong-secret")
                ));
        verifyNoInteractions(tokenManager);
    }
}

