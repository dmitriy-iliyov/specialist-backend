package com.specialist.auth.domain.access_token;

import com.specialist.auth.domain.access_token.models.AccessToken;
import com.specialist.auth.exceptions.AuthorizationHeaderFormatException;
import com.specialist.auth.exceptions.BlankTokenException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationConverter;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BearerAccessTokenAuthenticationConverter implements AuthenticationConverter {

    private final AccessTokenDeserializer accessTokenDeserializer;

    @Override
    public Authentication convert(HttpServletRequest request) {
        if ("/api/system/v1/auth/login".equals(request.getRequestURI())) {
            return null;
        }
        String header = request.getHeader("Authorization");
        if (header == null) {
            return null;
        }
        if (header.startsWith("Bearer ")) {
            String rawToken = header.substring("Bearer ".length()).trim();
            if (!rawToken.isBlank()) {
                AccessToken accessToken = accessTokenDeserializer.deserialize(rawToken);
                if (accessToken == null) {
                    return null;
                }
                return new PreAuthenticatedAuthenticationToken(accessToken, "Bearer Token");
            }
            throw new BlankTokenException();
        }
        throw new AuthorizationHeaderFormatException();
    }
}
