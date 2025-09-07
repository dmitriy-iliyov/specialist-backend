package com.specialist.auth.core.oauth2.services;

import com.specialist.auth.core.oauth2.models.Provider;
import org.springframework.security.oauth2.core.user.OAuth2User;

public interface OAuth2AccountPersistOrchestrator {
    void saveIfNonExists(Provider provider, OAuth2User oAuth2User);
}
