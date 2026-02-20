package com.specialist.specialistdirectory.ut.domain.language;

import com.specialist.specialistdirectory.domain.language.Language;
import com.specialist.specialistdirectory.domain.language.LanguageServiceImpl;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class LanguageServiceImplUnitTests {

    @Mock
    private HttpServletResponse response;

    @InjectMocks
    private LanguageServiceImpl service;

    @Test
    @DisplayName("UT: setLanguage() should add language cookie to response")
    void setLanguage_shouldAddCookie() {
        int code = Language.EN.getCode();
        ArgumentCaptor<Cookie> cookieCaptor = ArgumentCaptor.forClass(Cookie.class);

        service.setLanguage(code, response);

        verify(response).addCookie(cookieCaptor.capture());
        Cookie cookie = cookieCaptor.getValue();

        assertEquals("lang", cookie.getName());
        assertEquals("EN", cookie.getValue());
        assertEquals("/", cookie.getPath());
    }
}
