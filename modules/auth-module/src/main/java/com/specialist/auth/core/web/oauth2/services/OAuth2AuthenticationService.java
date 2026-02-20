package com.specialist.auth.core.web.oauth2.services;

import com.specialist.auth.core.web.oauth2.models.OAuth2InitialRequestEntity;
import com.specialist.auth.core.web.oauth2.models.OAuth2QueryParams;
import org.springframework.security.core.Authentication;

public interface OAuth2AuthenticationService {
    Authentication authenticate(OAuth2QueryParams params, OAuth2InitialRequestEntity initialRequest);
}
