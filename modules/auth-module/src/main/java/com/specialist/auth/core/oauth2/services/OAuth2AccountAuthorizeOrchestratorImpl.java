package com.specialist.auth.core.oauth2.services;

import com.specialist.auth.core.SessionCookieManager;
import com.specialist.auth.core.oauth2.OAuth2InitialRequestRepository;
import com.specialist.auth.core.oauth2.models.OAuth2InitialRequestEntity;
import com.specialist.auth.core.oauth2.models.OAuth2QueryParams;
import com.specialist.auth.core.oauth2.models.Provider;
import com.specialist.auth.domain.account.models.AccountUserDetails;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.web.OAuth2AuthorizationRequestResolver;
import org.springframework.security.oauth2.core.endpoint.OAuth2AuthorizationRequest;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.Duration;

@Service
@Slf4j
public class OAuth2AccountAuthorizeOrchestratorImpl implements OAuth2AccountAuthorizeOrchestrator {

    private final OAuth2AuthorizationRequestResolver authorizationRequestResolver;
    private final OAuth2InitialRequestRepository initialRequestRepository;
    private final OAuth2AuthenticationService authenticationService;
    private final SessionCookieManager sessionCookieManager;
    private final AuthenticationSuccessHandler successHandler;
    private final AuthenticationFailureHandler failureHandler;
    private final Duration STATE_TTL = Duration.ofMinutes(3);

    public OAuth2AccountAuthorizeOrchestratorImpl(OAuth2AuthorizationRequestResolver authorizationRequestResolver,
                                                  OAuth2InitialRequestRepository initialRequestRepository,
                                                  OAuth2AuthenticationService authenticationService,
                                                  SessionCookieManager sessionCookieManager,
                                                  @Qualifier("oAuth2AuthenticationSuccessHandler")
                                                  AuthenticationSuccessHandler successHandler,
                                                  @Qualifier("oAuth2AuthenticationFailureHandler")
                                                  AuthenticationFailureHandler failureHandler) {
        this.authorizationRequestResolver = authorizationRequestResolver;
        this.initialRequestRepository = initialRequestRepository;
        this.authenticationService = authenticationService;
        this.sessionCookieManager = sessionCookieManager;
        this.successHandler = successHandler;
        this.failureHandler = failureHandler;
    }

    @Override
    public String authorize(Provider provider, HttpServletRequest request) {
        OAuth2AuthorizationRequest authRequest = authorizationRequestResolver.resolve(
                request, provider.getRegistrationId()
        );
        initialRequestRepository.save(
                new OAuth2InitialRequestEntity(authRequest.getState(), authRequest.getRedirectUri(), STATE_TTL.getSeconds())
        );
        return authRequest.getAuthorizationRequestUri();
    }

    @Override
    public void callback(OAuth2QueryParams params, HttpServletRequest request, HttpServletResponse response) {
        OAuth2InitialRequestEntity requestEntity = initialRequestRepository.findById(params.state()).orElse(null);
        if (requestEntity == null) {
            throw new AuthenticationServiceException("Invalid state.");
        }
        initialRequestRepository.deleteById(requestEntity.state());
        try {
            Authentication authentication = authenticationService.authenticate(params, requestEntity);
            sessionCookieManager.create((AccountUserDetails) authentication.getPrincipal(), request, response);
            successHandler.onAuthenticationSuccess(request, response, authentication);
        } catch (AuthenticationException | IOException | ServletException e) {
            AuthenticationException authenticationException;
            if (!(e instanceof AuthenticationException)) {
                authenticationException = new AuthenticationServiceException("Unexpected exception.", e);
            } else {
                authenticationException = (AuthenticationException) e;
            }
            try {
                failureHandler.onAuthenticationFailure(request, response, authenticationException);
            } catch (IOException | ServletException ioe) {
                SecurityContextHolder.clearContext();
                log.error("Exception when oauth2 failure handler involved: {}", ioe.getMessage());
            }
        }
    }
}
