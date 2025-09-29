package com.specialist.auth.core.oauth2.services;

import com.specialist.auth.core.oauth2.models.OAuth2QueryParams;
import com.specialist.auth.core.oauth2.models.Provider;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public interface OAuth2AccountAuthorizeOrchestrator {
    String authorize(Provider provider, HttpServletRequest request);

    void callback(OAuth2QueryParams params, HttpServletRequest request, HttpServletResponse response);
}
