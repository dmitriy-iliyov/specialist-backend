package com.specialist.specialistdirectory.domain.language;

import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class LanguageControllerUnitTests {

    @Mock
    private LanguageService service;

    @Mock
    private HttpServletResponse response;

    @InjectMocks
    private LanguageController controller;

    @Test
    @DisplayName("UT: getAll() should return map of languages")
    void getAll_shouldReturnMap() {
        ResponseEntity<?> result = controller.getAll();

        assertEquals(HttpStatus.OK, result.getStatusCode());
        Map<Integer, Language> body = (Map<Integer, Language>) result.getBody();
        assertEquals(Language.values().length, body.size());
        assertTrue(body.containsKey(Language.EN.getCode()));
    }

    @Test
    @DisplayName("UT: setLanguage() should call service and return NO_CONTENT")
    void setLanguage_shouldCallService() {
        int code = 1;
        ResponseEntity<?> result = controller.setLanguage(code, response);

        assertEquals(HttpStatus.NO_CONTENT, result.getStatusCode());
        verify(service).setLanguage(code, response);
    }
}
