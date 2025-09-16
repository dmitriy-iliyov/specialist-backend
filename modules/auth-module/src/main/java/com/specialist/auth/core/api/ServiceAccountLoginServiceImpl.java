package com.specialist.auth.core.api;

import com.specialist.auth.core.TokenManager;
import com.specialist.auth.domain.service_account.models.ServiceAccountUserDetails;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class ServiceAccountLoginServiceImpl implements ServiceAccountLoginService {

    private final AuthenticationManager authenticationManager;
    private final TokenManager tokenManager;

    public ServiceAccountLoginServiceImpl(@Qualifier("serviceAccountAuthenticationManager")
                                               AuthenticationManager authenticationManager,
                                          TokenManager tokenManager) {
        this.authenticationManager = authenticationManager;
        this.tokenManager = tokenManager;
    }

    @Override
    public Map<String, String> login(ServiceLoginRequest loginRequest) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.clientId(), loginRequest.clientSecret())
            );
            SecurityContextHolder.getContext().setAuthentication(authentication);
            ServiceAccountUserDetails userDetails = (ServiceAccountUserDetails) authentication.getPrincipal();
            return Map.of("access_token", tokenManager.generate(userDetails).rawToken());
        } catch (AuthenticationException e) {
            SecurityContextHolder.clearContext();
            throw e;
        }
    }
}
