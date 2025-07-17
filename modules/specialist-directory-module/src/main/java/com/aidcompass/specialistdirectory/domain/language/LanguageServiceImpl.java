package com.aidcompass.specialistdirectory.domain.language;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LanguageServiceImpl implements LanguageService {

    @Override
    public void setLanguage(int code, HttpServletResponse response) {
        Cookie cookie = new Cookie("lang", Language.fromCode(code).toString());
        response.addCookie(cookie);
    }
}
