package com.aidcompass.core.security.configs;

import com.aidcompass.core.security.csrf.CsrfAuthenticationStrategy;
import com.aidcompass.core.security.csrf.CsrfMasker;
import com.aidcompass.core.security.csrf.CsrfMaskerImpl;
import com.aidcompass.core.security.handlers.CsrfAccessDeniedHandler;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.csrf.CsrfTokenRepository;


@Configuration
@RequiredArgsConstructor
public class CsrfToolsConfig {

    private final MessageSource messageSource;
    private final ObjectMapper mapper;


    @Bean
    public CsrfTokenRepository csrfTokenRepository(){
        CookieCsrfTokenRepository csrfTokenRepository = new CookieCsrfTokenRepository();
        csrfTokenRepository.setCookieCustomizer(cookie -> cookie
                .path("/")
                .domain(null)
                .httpOnly(true)
                .secure(true)
                .sameSite("None")
                .build());
        return csrfTokenRepository;
    }

    @Bean
    public CsrfAuthenticationStrategy csrfTokenAuthenticationStrategy(CsrfTokenRepository csrfTokenRepository){
        return new CsrfAuthenticationStrategy(csrfTokenRepository);
    }

    @Bean
    public CsrfMasker csrfTokenMasker() {
        return new CsrfMaskerImpl();
    }

    @Bean
    public CsrfAccessDeniedHandler csrfAccessDeniedHandler() {
        return new CsrfAccessDeniedHandler(messageSource, mapper);
    }
}