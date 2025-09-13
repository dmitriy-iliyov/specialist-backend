package com.specialist.auth.core.csrf;

public interface CsrfTokenMasker {
    String mask(String token);
}
