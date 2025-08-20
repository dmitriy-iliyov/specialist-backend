package com.specialist.auth.core.oauth2;

import com.specialist.auth.core.oauth2.provider.Provider;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public interface OAuth2AccountAuthService {
    String authorize(Provider provider, HttpServletRequest request);

    void callback(Provider provider, OAuth2QueryParams params, HttpServletRequest request, HttpServletResponse response);
}
