package com.specialist.auth.core.oauth2;

import com.specialist.auth.core.oauth2.provider.Provider;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.authentication.OAuth2LoginAuthenticationToken;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.web.OAuth2AuthorizationRequestResolver;
import org.springframework.security.oauth2.core.endpoint.OAuth2AuthorizationExchange;
import org.springframework.security.oauth2.core.endpoint.OAuth2AuthorizationRequest;
import org.springframework.security.oauth2.core.endpoint.OAuth2AuthorizationResponse;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.Duration;

@Service
@Slf4j
public class OAuth2AccountAuthServiceImpl implements OAuth2AccountAuthService {

    private final OAuth2AuthorizationRequestResolver oAuth2AuthorizationRequestResolver;
    private final OAuth2StateRepository stateRepository;
    private final ClientRegistrationRepository clientRegistrationRepository;
    private final AuthenticationManager authenticationManager;
    private final AuthenticationSuccessHandler successHandler;
    private final AuthenticationFailureHandler failureHandler;
    private final Duration STATE_TTL = Duration.ofMinutes(3);

    public OAuth2AccountAuthServiceImpl(OAuth2AuthorizationRequestResolver oAuth2AuthorizationRequestResolver,
                                        OAuth2StateRepository stateRepository, ClientRegistrationRepository clientRegistrationRepository,
                                        @Qualifier("accountAuthenticationManager") AuthenticationManager authenticationManager,
                                        @Qualifier("oAuth2AuthenticationSuccessHandler") AuthenticationSuccessHandler successHandler,
                                        @Qualifier("oAuth2AuthenticationFailureHandler") AuthenticationFailureHandler failureHandler) {
        this.oAuth2AuthorizationRequestResolver = oAuth2AuthorizationRequestResolver;
        this.stateRepository = stateRepository;
        this.clientRegistrationRepository = clientRegistrationRepository;
        this.authenticationManager = authenticationManager;
        this.successHandler = successHandler;
        this.failureHandler = failureHandler;
    }

    @Override
    public String authorize(Provider provider, HttpServletRequest request) {
        OAuth2AuthorizationRequest authorizationRequest = oAuth2AuthorizationRequestResolver.resolve(request, provider.getRegistrationId());
        stateRepository.save(new OAuth2StateEntity(authorizationRequest.getState(), STATE_TTL));
        return authorizationRequest.getAuthorizationRequestUri();
    }

    @Override
    public void callback(Provider provider, OAuth2QueryParams params, HttpServletRequest request, HttpServletResponse response) {
        OAuth2StateEntity stateEntity = stateRepository.findById(params.state()).orElse(null);
        if (stateEntity == null) {
            throw new AuthenticationServiceException("Invalid state.");
        }
        stateRepository.deleteById(stateEntity.state());
        try {
            ClientRegistration clientRegistration = clientRegistrationRepository.findByRegistrationId(provider.getRegistrationId());
            OAuth2AuthorizationRequest authorizationRequest = OAuth2AuthorizationRequest
                    .authorizationCode()
                    .authorizationUri(clientRegistration.getProviderDetails().getAuthorizationUri())
                    .clientId(clientRegistration.getClientId())
                    .redirectUri(params.redirectUrl())
                    .state(params.state())
                    .build();
            OAuth2AuthorizationResponse authorizationResponse =
                    OAuth2AuthorizationResponse.success(params.code())
                            .redirectUri(params.redirectUrl())
                            .state(params.state())
                            .build();
            OAuth2AuthorizationExchange exchange = new OAuth2AuthorizationExchange(authorizationRequest, authorizationResponse);
            Authentication authentication = authenticationManager.authenticate(
                    new OAuth2LoginAuthenticationToken(clientRegistration, exchange)
            );
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
                log.error("Exception when oauth authorize: {}", ioe.getMessage());
            }
        }
    }
}
