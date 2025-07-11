package com.aidcompass.core.security.csrf;

public interface CsrfMasker {

    String mask(String csrfToken) throws Exception;

}
