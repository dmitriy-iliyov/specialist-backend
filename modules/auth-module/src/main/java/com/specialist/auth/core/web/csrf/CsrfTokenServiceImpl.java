package com.specialist.auth.core.web.csrf;

import com.specialist.auth.exceptions.CsrfTokenMaskingException;
import com.specialist.auth.exceptions.CsrfTokenNullException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.security.web.csrf.CsrfTokenRepository;
import org.springframework.security.web.csrf.DefaultCsrfToken;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CsrfTokenServiceImpl implements CsrfTokenService {

    private final CsrfTokenRepository repository;
    private final CsrfTokenMasker masker;

    @Override
    public void onAuthentication(HttpServletRequest request, HttpServletResponse response) {
        CsrfToken csrfToken = repository.generateToken(request);
        repository.saveToken(csrfToken, request, response);
    }

    @Override
    public CsrfToken getMaskedToken(HttpServletRequest request) {
        CsrfToken csrfToken = repository.loadToken(request);
        CsrfToken maskedCsrfToken;
        try {
            maskedCsrfToken = new DefaultCsrfToken(
                    csrfToken.getHeaderName(),
                    csrfToken.getParameterName(),
                    masker.mask(csrfToken.getToken())
            );
        } catch(Exception e) {
            if (e instanceof NullPointerException) {
                throw new CsrfTokenNullException();
            }
            throw new CsrfTokenMaskingException();
        }
        return maskedCsrfToken;
    }
}
