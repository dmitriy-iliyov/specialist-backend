package com.specialist.auth.ut.domain.access_token;

import com.specialist.auth.domain.access_token.AccessTokenDeserializer;
import com.specialist.auth.domain.access_token.CookieAccessTokenAuthenticationConverter;
import com.specialist.auth.domain.access_token.models.AccessToken;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CookieAccessTokenAuthenticationConverterUnitTests {

    @Mock
    private AccessTokenDeserializer deserializer;

    @Mock
    private HttpServletRequest request;

    private final AccessToken accessToken = new AccessToken(
            UUID.randomUUID(), UUID.randomUUID(), List.of(), Instant.now(), Instant.now()
    );

    @InjectMocks
    private CookieAccessTokenAuthenticationConverter converter;

    @Test
    @DisplayName("UT: convert() should return null when no cookies")
    void convert_noCookies_returnsNull() {
        when(request.getCookies()).thenReturn(null);

        assertNull(converter.convert(request));

        verify(request, times(1)).getCookies();
        verifyNoInteractions(deserializer);
    }

    @Test
    @DisplayName("UT: convert() should return null when no matching cookie name")
    void convert_noMatchingCookie_returnsNull() {
        Cookie otherCookie = new Cookie("other", "value");
        when(request.getCookies()).thenReturn(new Cookie[]{otherCookie});

        assertNull(converter.convert(request));

        verify(request, times(2)).getCookies();
        verifyNoInteractions(deserializer);
    }

    @Test
    @DisplayName("UT: convert() should return null when deserializer returns null")
    void convert_matchingCookieButDeserializerReturnsNull_throws() {
        Cookie cookie = new Cookie("__Host-access-token", "tokenValue");
        when(request.getCookies()).thenReturn(new Cookie[]{cookie});
        when(deserializer.deserialize("tokenValue")).thenReturn(null);

        Authentication authentication = converter.convert(request);

        assertNull(authentication);
        verify(request, times(2)).getCookies();
        verify(deserializer, times(1)).deserialize("tokenValue");
    }

    @Test
    @DisplayName("UT: convert() should return PreAuthenticatedAuthenticationToken on valid token")
    void convert_validCookie_returnsAuthentication() {
        Cookie cookie = new Cookie("__Host-access-token", "tokenValue");
        when(request.getCookies()).thenReturn(new Cookie[]{cookie});
        when(deserializer.deserialize("tokenValue")).thenReturn(accessToken);

        Authentication auth = converter.convert(request);

        assertNotNull(auth);
        assertTrue(auth instanceof PreAuthenticatedAuthenticationToken);
        assertEquals(accessToken, auth.getPrincipal());

        verify(request, times(2)).getCookies();
        verify(deserializer, times(1)).deserialize("tokenValue");
    }
}