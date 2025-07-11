package com.aidcompass.core.security.csrf;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.session.SessionAuthenticationException;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.security.web.csrf.CsrfTokenRepository;

@Log4j2
@RequiredArgsConstructor
public class CsrfAuthenticationStrategy {

    private final CsrfTokenRepository csrfTokenRepository;


    public void onAuthentication(HttpServletRequest request, HttpServletResponse response) throws SessionAuthenticationException {
        CsrfToken csrfToken = csrfTokenRepository.generateToken(request);
        csrfTokenRepository.saveToken(csrfToken, request, response);
    }
}
