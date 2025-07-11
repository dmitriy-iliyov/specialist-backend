package com.aidcompass.core.security.converters;

import com.aidcompass.core.security.domain.token.models.Token;
import com.aidcompass.core.security.domain.token.models.TokenType;
import com.aidcompass.core.security.domain.token.serializing.TokenDeserializer;
import com.aidcompass.core.security.exceptions.BearerJwtAuthorizationException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationConverter;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;

@RequiredArgsConstructor
public class BearerJwtAuthenticationConverter implements AuthenticationConverter {

    private final TokenDeserializer tokenDeserializer;


    @Override
    public Authentication convert(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (bearerToken != null) {
            String [] splitBearerToken = bearerToken.split(" ");
            if (splitBearerToken[0].equals("Bearer")) {
                if (splitBearerToken.length == 2) {
                    String jwt = splitBearerToken[1];
                    if (jwt != null) {
                        Token token = tokenDeserializer.deserialize(jwt);
                        if (token != null) {
                            if (token.getType() == TokenType.SERVICE) {
                                return new PreAuthenticatedAuthenticationToken(token, "Bearer Token");
                            }
                            throw new BearerJwtAuthorizationException("Bearer token has invalid type!");
                        }
                    }
                    throw new BearerJwtAuthorizationException("Bearer token is null!");
                }
                throw new BearerJwtAuthorizationException("Bearer token is empty!");
            }
            throw new BearerJwtAuthorizationException("Bearer part is missing!");
        }
        return null;
    }
}