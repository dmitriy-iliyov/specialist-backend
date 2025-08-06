package com.aidcompass.auth.core.csrf;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.web.csrf.CsrfToken;

public interface CsrfTokenService {
    void onAuthentication(HttpServletRequest request, HttpServletResponse response);

    CsrfToken getMaskedToken(HttpServletRequest request);
}
