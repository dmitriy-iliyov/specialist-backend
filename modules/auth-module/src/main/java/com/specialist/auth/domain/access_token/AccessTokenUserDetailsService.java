package com.specialist.auth.domain.access_token;

import com.specialist.auth.domain.access_token.models.AccessToken;
import com.specialist.auth.domain.access_token.models.AccessTokenUserDetails;
import com.specialist.auth.domain.refresh_token.RefreshTokenService;
import com.specialist.auth.exceptions.RefreshTokenExpiredException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.AuthenticationUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
public class AccessTokenUserDetailsService implements AuthenticationUserDetailsService<PreAuthenticatedAuthenticationToken> {

    private final RefreshTokenService service;

    @Transactional(readOnly = true)
    @Override
    public UserDetails loadUserDetails(PreAuthenticatedAuthenticationToken token) throws UsernameNotFoundException {
        if (token != null) {
            AccessToken accessToken = (AccessToken) token.getPrincipal();
            if (accessToken != null) {
                if (!service.isActiveById(accessToken.id())) {
                    throw new RefreshTokenExpiredException();
                }
                List<SimpleGrantedAuthority> authorities = accessToken.authorities().stream()
                        .map(SimpleGrantedAuthority::new)
                        .toList();
                return AccessTokenUserDetails.builder()
                        .id(accessToken.id())
                        .userId(accessToken.subjectId())
                        .authorities(authorities)
                        .build();
            }
        }
        return null;
    }
}
