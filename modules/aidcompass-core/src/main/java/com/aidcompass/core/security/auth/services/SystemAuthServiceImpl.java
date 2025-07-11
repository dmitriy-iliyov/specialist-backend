package com.aidcompass.core.security.auth.services;

import com.aidcompass.core.security.auth.dto.ServiceAuthRequest;
import com.aidcompass.core.security.domain.service.ServiceUserDetails;
import com.aidcompass.core.security.domain.token.factory.TokenFactory;
import com.aidcompass.core.security.domain.token.models.Token;
import com.aidcompass.core.security.domain.token.models.TokenType;
import com.aidcompass.core.security.domain.token.serializing.TokenSerializer;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Map;

@Service
public class SystemAuthServiceImpl implements SystemAuthService {

    private final AuthenticationManager authenticationManager;
    private final UserDetailsService userDetailsService;
    private final TokenFactory tokenFactory;
    private final TokenSerializer tokenSerializer;


    public SystemAuthServiceImpl(@Qualifier("serviceAuthenticationManager") AuthenticationManager authenticationManager,
                                 @Qualifier("serviceUserDetailsService") UserDetailsService userDetailsService,
                                 TokenFactory tokenFactory,
                                 TokenSerializer tokenSerializer) {
        this.authenticationManager = authenticationManager;
        this.userDetailsService = userDetailsService;
        this.tokenFactory = tokenFactory;
        this.tokenSerializer = tokenSerializer;
    }

    @Override
    public Map<String, String> login(ServiceAuthRequest requestDto) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(requestDto.name(), requestDto.key())
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        Token token = tokenFactory.generateToken(
                (ServiceUserDetails) authentication.getPrincipal(),
                TokenType.SERVICE,
                Duration.ofDays(2)
        );
        return Map.of("token", tokenSerializer.serialize(token));
    }

    @Override
    public Map<String, String> generateToken(String serviceName, Integer daysTtl) {
        ServiceUserDetails userDetails = (ServiceUserDetails) userDetailsService.loadUserByUsername(serviceName);
        Token token = tokenFactory.generateToken(userDetails, TokenType.SERVICE, Duration.ofDays(daysTtl));
        return Map.of("token", tokenSerializer.serialize(token));
    }
}
