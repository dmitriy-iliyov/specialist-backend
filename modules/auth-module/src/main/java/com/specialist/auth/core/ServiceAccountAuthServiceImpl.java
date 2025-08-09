package com.specialist.auth.core;

import com.specialist.auth.core.models.ServiceLoginRequest;
import com.specialist.auth.domain.service_account.models.ServiceAccountUserDetails;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class ServiceAccountAuthServiceImpl implements ServiceAccountAuthService {

    private final AuthenticationManager authenticationManager;
    private final TokenManager tokenManager;

    public ServiceAccountAuthServiceImpl(@Qualifier("serviceAccountAuthenticationManager") AuthenticationManager authenticationManager,
                                         TokenManager tokenManager) {
        this.authenticationManager = authenticationManager;
        this.tokenManager = tokenManager;
    }

    @Override
    public Map<String, String> login(ServiceLoginRequest requestDto, HttpServletRequest request) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(requestDto.clientId(), requestDto.clientSecret())
            );
            SecurityContextHolder.getContext().setAuthentication(authentication);
            ServiceAccountUserDetails userDetails = (ServiceAccountUserDetails) authentication.getPrincipal();
            return tokenManager.generate(userDetails);
        } catch (AuthenticationException e) {
            SecurityContextHolder.clearContext();
            return null;
        }
    }
}
