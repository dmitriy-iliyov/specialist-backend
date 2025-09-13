package com.specialist.auth.core.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.csrf.CsrfTokenRepository;

@Configuration
public class CsrfConfig {

    @Bean
    public CsrfTokenRepository csrfTokenRepository() {
        CookieCsrfTokenRepository repository = new CookieCsrfTokenRepository();
        repository.setCookieCustomizer(cookie -> cookie
                .path("/")
                .domain(null)
                .httpOnly(true)
                .secure(true)
                .sameSite("None")
                .build()
        );
        return repository;
    }
}
