package com.aidcompass.core.security.converters;


import com.aidcompass.core.security.domain.token.models.Token;
import com.aidcompass.core.security.domain.token.models.TokenType;
import com.aidcompass.core.security.domain.token.serializing.TokenDeserializer;
import com.aidcompass.core.security.exceptions.CookieJwtAuthorizationException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationConverter;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;

import java.util.stream.Stream;


@RequiredArgsConstructor
public class CookieJwtAuthenticationConverter implements AuthenticationConverter {

    private final TokenDeserializer tokenDeserializer;

    @Override
    public Authentication convert(HttpServletRequest request) {
        if(request.getCookies() != null) {
            return Stream.of(request.getCookies())
                    .filter(cookie -> cookie.getName().equals("__Host-auth_token"))
                    .findFirst()
                    .map(cookie -> {
                        Token token = tokenDeserializer.deserialize(cookie.getValue());
                        if (token.getType() == TokenType.USER) {
                            return new PreAuthenticatedAuthenticationToken(token, cookie.getValue());
                        }
                        throw new CookieJwtAuthorizationException("Token has invalid type!");
                    }).orElse(null);
        }
        return null;
    }

}
