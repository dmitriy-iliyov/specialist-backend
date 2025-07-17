package com.aidcompass.specialistdirectory.domain.language;

import jakarta.servlet.http.HttpServletResponse;

public interface LanguageService {
    void setLanguage(int code, HttpServletResponse response);
}
