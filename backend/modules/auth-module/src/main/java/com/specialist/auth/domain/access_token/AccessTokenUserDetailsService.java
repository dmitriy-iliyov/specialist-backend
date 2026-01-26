package com.specialist.auth.domain.access_token;

import com.specialist.auth.domain.access_token.models.AccessToken;
import com.specialist.auth.domain.access_token.models.AccessTokenUserDetails;
import com.specialist.auth.domain.refresh_token.RefreshTokenService;
import com.specialist.auth.domain.refresh_token.models.RefreshToken;
import com.specialist.auth.domain.refresh_token.models.RefreshTokenUserDetails;
import com.specialist.auth.exceptions.RefreshTokenExpiredException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.AuthenticationUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AccessTokenUserDetailsService implements AuthenticationUserDetailsService<PreAuthenticatedAuthenticationToken> {

    private final RefreshTokenService service;

    @Transactional(readOnly = true)
    @Override
    public UserDetails loadUserDetails(PreAuthenticatedAuthenticationToken token) throws UsernameNotFoundException {
        if (token != null) {
            Object principal = token.getPrincipal();
            if (principal == null) {
                throw new BadCredentialsException("principal is null");
            }
            if (principal instanceof AccessToken accessToken) {
                if (!service.isActiveById(accessToken.id())) {
                    throw new RefreshTokenExpiredException();
                }
                List<SimpleGrantedAuthority> authorities = accessToken.authorities().stream()
                        .map(SimpleGrantedAuthority::new)
                        .toList();
                return AccessTokenUserDetails.builder()
                        .id(accessToken.id())
                        .accountId(accessToken.accountId())
                        .authorities(authorities)
                        .build();
            } else if (principal instanceof String rawRefreshTokenId) {
                String decodedRawRefreshTokenId = new String(
                        Base64.getUrlDecoder().decode(rawRefreshTokenId),
                        StandardCharsets.UTF_8
                );
                UUID refreshTokenId = UUID.fromString(decodedRawRefreshTokenId);
                RefreshToken refreshToken = service.findById(refreshTokenId);
                if (refreshToken == null) {
                    throw new RefreshTokenExpiredException();
                }
                List<SimpleGrantedAuthority> authorities = refreshToken.authorities().stream()
                        .map(SimpleGrantedAuthority::new)
                        .toList();
                return new RefreshTokenUserDetails(
                        refreshToken.id(),
                        refreshToken.accountId(),
                        authorities
                );
            }
        }
        return null;
    }
}
