package com.specialist.auth.core.web.oauth2;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.specialist.auth.domain.account.models.AccountUserDetails;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component("oAuth2AuthenticationSuccessHandler")
public class OAuth2AuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    private final OAuth2UserRepository oAuth2UserRepository;
    private final ObjectMapper objectMapper;

    public OAuth2AuthenticationSuccessHandler(OAuth2UserRepository oAuth2UserRepository, ObjectMapper objectMapper) {
        this.oAuth2UserRepository = oAuth2UserRepository;
        this.objectMapper = objectMapper;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String email= ((AccountUserDetails) authentication.getPrincipal()).getEmail();
        response.setContentType("application/json");
        response.getWriter().write(objectMapper.writeValueAsString(oAuth2UserRepository.findById(email)));
    }
}
