package com.specialist.auth.core.web.oauth2;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.specialist.auth.core.web.oauth2.models.OAuth2UserEntity;
import com.specialist.auth.domain.account.models.AccountUserDetails;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Optional;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class OAuth2AuthenticationSuccessHandlerUnitTests {

    @Mock
    private OAuth2UserRepository oAuth2UserRepository;

    @Mock
    private ObjectMapper objectMapper;

    private OAuth2AuthenticationSuccessHandler successHandler;

    @BeforeEach
    void setUp() {
        successHandler = new OAuth2AuthenticationSuccessHandler(oAuth2UserRepository, objectMapper);
    }

    @Test
    @DisplayName("UT: onAuthenticationSuccess should write user details to response")
    void onAuthenticationSuccess_shouldWriteUserDetails() throws IOException {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        Authentication authentication = mock(Authentication.class);
        AccountUserDetails userDetails = mock(AccountUserDetails.class);
        PrintWriter writer = mock(PrintWriter.class);
        String email = "test@example.com";
        OAuth2UserEntity oAuth2UserEntity = new OAuth2UserEntity();
        Optional<OAuth2UserEntity> optionalUser = Optional.of(oAuth2UserEntity);

        when(authentication.getPrincipal()).thenReturn(userDetails);
        when(userDetails.getEmail()).thenReturn(email);
        when(response.getWriter()).thenReturn(writer);
        when(oAuth2UserRepository.findById(email)).thenReturn(optionalUser);
        when(objectMapper.writeValueAsString(optionalUser)).thenReturn("json_response");

        successHandler.onAuthenticationSuccess(request, response, authentication);

        verify(response, times(1)).setContentType("application/json");
        verify(oAuth2UserRepository, times(1)).findById(email);
        verify(objectMapper, times(1)).writeValueAsString(optionalUser);
        verify(writer, times(1)).write("json_response");
    }
}
