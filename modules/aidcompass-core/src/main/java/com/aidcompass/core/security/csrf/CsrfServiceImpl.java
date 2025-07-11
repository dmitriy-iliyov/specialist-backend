package com.aidcompass.core.security.csrf;

import com.aidcompass.core.security.exceptions.CsrfTokenMaskingException;
import com.aidcompass.core.security.exceptions.NullCsrfTokenException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.security.web.csrf.CsrfTokenRepository;
import org.springframework.security.web.csrf.DefaultCsrfToken;
import org.springframework.stereotype.Service;

@Log4j2
@Service
@RequiredArgsConstructor
public class CsrfServiceImpl implements CsrfService {

    private final CsrfTokenRepository csrfTokenRepository;
    private final CsrfMasker csrfMasker;

    @Override
    public CsrfToken getMaskedToken(HttpServletRequest request) {
        CsrfToken csrfToken = csrfTokenRepository.loadToken(request);
        CsrfToken maskedCsrfToken;
        try {
            maskedCsrfToken = new DefaultCsrfToken(csrfToken.getHeaderName(),
                    csrfToken.getParameterName(), csrfMasker.mask(csrfToken.getToken()));
        } catch (Exception e) {
            log.error("Error while getting csrf token : ", e);
            if (e instanceof NullPointerException) {
                throw new NullCsrfTokenException();
            }
            throw new CsrfTokenMaskingException();
        }
        return maskedCsrfToken;
    }
}
