package com.specialist.auth.core.oauth2;

import com.specialist.auth.core.oauth2.provider.Provider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DefaultOAuth2UserServiceDecorator extends DefaultOAuth2UserService {

    private final OAuth2AccountPersistOrchestrator persistOrchestrator;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        DefaultOAuth2User oAuth2User = (DefaultOAuth2User) super.loadUser(userRequest);
        Provider provider = Provider.fromName(userRequest.getClientRegistration().getRegistrationId());
        persistOrchestrator.saveIfNonExists(provider, oAuth2User);
        return oAuth2User;
    }
}
