package com.specialist.auth.core.oauth2.services;

import com.specialist.auth.core.oauth2.models.Provider;
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

    private final OAuth2AccountPersistService persistService;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        DefaultOAuth2User oAuth2User = (DefaultOAuth2User) super.loadUser(userRequest);
        Provider provider = Provider.fromJson(userRequest.getClientRegistration().getRegistrationId());
        persistService.saveIfNonExists(provider, oAuth2User);
        return oAuth2User;
    }
}
