package com.specialist.auth.domain.access_token;

import com.specialist.auth.domain.access_token.models.AccessToken;
import com.specialist.auth.exceptions.AccessTokenExpiredException;
import com.specialist.auth.exceptions.AuthorizationHeaderFormatException;
import com.specialist.auth.exceptions.BlankTokenException;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BearerAccessTokenAuthenticationConverterUnitTests {

    @Mock
    private AccessTokenDeserializer deserializer;

    @Mock
    private HttpServletRequest request;

    @InjectMocks
    private BearerAccessTokenAuthenticationConverter converter;

    @Test
    @DisplayName("UT: convert() should return null when Authorization header is missing")
    void convert_shouldReturnNull_whenHeaderMissing() {
        when(request.getHeader("Authorization")).thenReturn(null);

        assertNull(converter.convert(request));

        verify(request, times(1)).getHeader("Authorization");
        verifyNoInteractions(deserializer);
        verifyNoMoreInteractions(request);
    }

    @Test
    @DisplayName("UT: convert() should throw AuthorizationHeaderFormatException when header does not start with Bearer")
    void convert_shouldThrow_whenHeaderNotBearer() {
        when(request.getHeader("Authorization")).thenReturn("Basic abc123");

        assertThrows(AuthorizationHeaderFormatException.class, () -> converter.convert(request));

        verify(request, times(1)).getHeader("Authorization");
        verifyNoInteractions(deserializer);
        verifyNoMoreInteractions(request);
    }

    @Test
    @DisplayName("UT: convert() should throw BlankTokenException when token is blank")
    void convert_shouldThrow_whenTokenBlank() {
        when(request.getHeader("Authorization")).thenReturn("Bearer    ");

        assertThrows(BlankTokenException.class, () -> converter.convert(request));

        verify(request, times(1)).getHeader("Authorization");
        verifyNoInteractions(deserializer);
        verifyNoMoreInteractions(request);
    }

    @Test
    @DisplayName("UT: convert() should throw AccessTokenExpiredException when deserializer returns null")
    void convert_shouldThrow_whenTokenExpired() {
        String token = "validToken";
        when(request.getHeader("Authorization")).thenReturn("Bearer " + token);
        when(deserializer.deserialize(token)).thenReturn(null);

        assertThrows(AccessTokenExpiredException.class, () -> converter.convert(request));

        verify(request, times(1)).getHeader("Authorization");
        verify(deserializer, times(1)).deserialize(token);
        verifyNoMoreInteractions(request, deserializer);
    }

    @Test
    @DisplayName("UT: convert() should return PreAuthenticatedAuthenticationToken when token is valid")
    void convert_shouldReturnAuthToken_whenValidToken() {
        String token = "validToken";
        AccessToken accessToken = mock(AccessToken.class);
        when(request.getHeader("Authorization")).thenReturn("Bearer " + token);
        when(deserializer.deserialize(token)).thenReturn(accessToken);

        var result = converter.convert(request);

        assertNotNull(result);
        assertTrue(result instanceof PreAuthenticatedAuthenticationToken);
        assertEquals(accessToken, result.getPrincipal());
        assertEquals("validToken", result.getCredentials());

        verify(request, times(1)).getHeader("Authorization");
        verify(deserializer, times(1)).deserialize(token);
        verifyNoMoreInteractions(request, deserializer);
    }
}
