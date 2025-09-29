package com.specialist.auth.core.web.csrf;

public interface CsrfTokenMasker {
    String mask(String token);
}
