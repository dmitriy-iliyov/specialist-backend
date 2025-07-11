package com.aidcompass.core.security.cookie;

import com.aidcompass.core.security.domain.authority.models.Authority;
import jakarta.servlet.http.Cookie;
import org.springframework.security.core.Authentication;

public interface CookieFactory {
    Cookie generateAuthCookie(Authentication authentication);

    Cookie generateInfoCookie(Authority authority);
}
