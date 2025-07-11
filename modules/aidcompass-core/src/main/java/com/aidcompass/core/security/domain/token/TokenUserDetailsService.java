package com.aidcompass.core.security.domain.token;

import com.aidcompass.core.security.domain.token.models.Token;
import com.aidcompass.core.security.domain.token.models.TokenEntity;
import com.aidcompass.core.security.domain.token.models.TokenUserDetails;
import com.aidcompass.core.security.exceptions.illegal_input.InvalidPrincipalPassed;
import com.aidcompass.core.security.exceptions.illegal_input.TokenExpired;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.AuthenticationUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class TokenUserDetailsService implements AuthenticationUserDetailsService<PreAuthenticatedAuthenticationToken> {

    private final DeactivateTokenRepository deactivateTokenRepository;

    @Override
    public UserDetails loadUserDetails(PreAuthenticatedAuthenticationToken preAuthenticatedAuthenticationToken)
            throws UsernameNotFoundException {
        if (preAuthenticatedAuthenticationToken.getPrincipal() instanceof Token token) {
            if(!deactivateTokenRepository.existsById(token.getId())){
                return TokenUserDetails.build(token);
            } else {
                throw new TokenExpired();
            }
        }
        throw new InvalidPrincipalPassed();
    }

    @Transactional
    public void save(TokenEntity token) {
        deactivateTokenRepository.save(token);
    }
}
