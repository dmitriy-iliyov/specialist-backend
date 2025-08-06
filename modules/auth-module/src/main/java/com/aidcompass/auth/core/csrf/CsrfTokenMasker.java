package com.aidcompass.auth.core.csrf;

public interface CsrfTokenMasker {
    String mask(String token);
}
