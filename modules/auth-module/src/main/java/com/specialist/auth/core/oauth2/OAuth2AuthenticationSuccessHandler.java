package com.specialist.auth.core.oauth2;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.specialist.auth.core.AuthCookieFactory;
import com.specialist.auth.core.TokenManager;
import com.specialist.auth.core.csrf.CsrfTokenService;
import com.specialist.auth.core.models.Token;
import com.specialist.auth.core.models.TokenType;
import com.specialist.auth.domain.account.models.AccountUserDetails;
import jakarta.activation.MimeType;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jdk.jfr.ContentType;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;

@Component
public class OAuth2AuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    private final UserDetailsService userDetailsService;
    private final TokenManager tokenManager;
    private final CsrfTokenService csrfTokenService;
    private final OAuth2UserRepository oAuth2UserRepository;
    private final ObjectMapper objectMapper;

    public OAuth2AuthenticationSuccessHandler(@Qualifier("unifiedAccountService") UserDetailsService userDetailsService,
                                              TokenManager tokenManager, CsrfTokenService csrfTokenService,
                                              OAuth2UserRepository oAuth2UserRepository, ObjectMapper objectMapper) {
        this.userDetailsService = userDetailsService;
        this.tokenManager = tokenManager;
        this.csrfTokenService = csrfTokenService;
        this.oAuth2UserRepository = oAuth2UserRepository;
        this.objectMapper = objectMapper;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        DefaultOAuth2User principal = (DefaultOAuth2User) authentication.getPrincipal();
        String email = principal.getAttribute("email");
        if (email == null) {
            throw new RuntimeException("Null email from oauth2 user attributes.");
        }
        AccountUserDetails userDetails = (AccountUserDetails) userDetailsService.loadUserByUsername(email);
        Map<TokenType, Token> tokens = tokenManager.generate(userDetails);
        csrfTokenService.onAuthentication(request, response);
        for (Token token: tokens.values()) {
            response.addCookie(AuthCookieFactory.generate(token.rawToken(), token.expiresAt(), token.type()));
        }
        csrfTokenService.onAuthentication(request, response);
        response.setContentType("application/json");
        response.getWriter().write(objectMapper.writeValueAsString(oAuth2UserRepository.findById(email)));
    }
}
