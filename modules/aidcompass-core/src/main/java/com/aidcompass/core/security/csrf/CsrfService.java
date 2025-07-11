package com.aidcompass.core.security.csrf;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.web.csrf.CsrfToken;

public interface CsrfService {
    CsrfToken getMaskedToken(HttpServletRequest request);
}
