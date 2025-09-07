package com.specialist.auth.core.oauth2.services;

import com.specialist.auth.core.oauth2.models.OAuth2InitialRequestEntity;
import com.specialist.auth.core.oauth2.models.OAuth2QueryParams;
import com.specialist.auth.core.oauth2.models.Provider;
import com.specialist.auth.domain.account.models.AccountUserDetails;
import com.specialist.auth.exceptions.InvalidProviderException;
import com.specialist.auth.exceptions.OAuth2UserNullEmailException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.client.authentication.OAuth2LoginAuthenticationToken;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.core.endpoint.OAuth2AuthorizationExchange;
import org.springframework.security.oauth2.core.endpoint.OAuth2AuthorizationRequest;
import org.springframework.security.oauth2.core.endpoint.OAuth2AuthorizationResponse;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
public class OAuth2AuthenticationServiceImpl implements OAuth2AuthenticationService {

    private final ClientRegistrationRepository clientRegistrationRepository;
    private final UserDetailsService userDetailsService;
    private final AuthenticationManager authenticationManager;

    public OAuth2AuthenticationServiceImpl(ClientRegistrationRepository clientRegistrationRepository,
                                           @Qualifier("accountUserDetailsService") UserDetailsService userDetailsService,
                                           @Qualifier("accountAuthenticationManager") AuthenticationManager authenticationManager) {
        this.clientRegistrationRepository = clientRegistrationRepository;
        this.userDetailsService = userDetailsService;
        this.authenticationManager = authenticationManager;
    }

    @Override
    public Authentication authenticate(OAuth2QueryParams params, OAuth2InitialRequestEntity initialRequest, Provider provider) {
        ClientRegistration clientRegistration = clientRegistrationRepository.findByRegistrationId(provider.getRegistrationId());
        OAuth2AuthorizationRequest authorizationRequest = OAuth2AuthorizationRequest
                .authorizationCode()
                .authorizationUri(clientRegistration.getProviderDetails().getAuthorizationUri())
                .redirectUri(initialRequest.redirectUri())
                .clientId(clientRegistration.getClientId())
                .state(params.state())
                .build();
        OAuth2AuthorizationResponse authorizationResponse =
                OAuth2AuthorizationResponse.success(params.code())
                        .state(params.state())
                        .redirectUri(initialRequest.redirectUri())
                        .build();
        OAuth2AuthorizationExchange exchange = new OAuth2AuthorizationExchange(authorizationRequest, authorizationResponse);
        Authentication oAuth2Authentication = authenticationManager.authenticate(
                new OAuth2LoginAuthenticationToken(clientRegistration, exchange)
        );
        DefaultOAuth2User principal = (DefaultOAuth2User) oAuth2Authentication.getPrincipal();
        String email = principal.getAttribute("email");
        if (email == null) {
            throw new OAuth2UserNullEmailException();
        }
        AccountUserDetails userDetails = (AccountUserDetails) userDetailsService.loadUserByUsername(email);
        if (userDetails.getProvider().equals(Provider.LOCAL)) {
            throw new InvalidProviderException(provider);
        }
        return new PreAuthenticatedAuthenticationToken(userDetails, "none");
    }
}
