package com.specialist.auth.domain.access_token;

import com.specialist.auth.domain.access_token.models.AccessToken;
import com.specialist.auth.exceptions.AccessTokenExpiredException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationConverter;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.stereotype.Component;

import java.util.stream.Stream;

@Component
@RequiredArgsConstructor
public class CookieAccessTokenAuthenticationConverter implements AuthenticationConverter {

    private final AccessTokenDeserializer deserializer;

    @Override
    public Authentication convert(HttpServletRequest request) {
        if (request.getCookies() != null) {
            return Stream.of(request.getCookies())
                    .filter(c -> c.getName().equals("__Host-access-token"))
                    .findFirst()
                    .map(cookie -> {
                        AccessToken accessToken = deserializer.deserialize(cookie.getValue());
                        if (accessToken == null) {
                            throw new AccessTokenExpiredException();
                        }
                        return new PreAuthenticatedAuthenticationToken(accessToken, null);
                    })
                    .orElse(null);
        }
        return null;
    }
}
