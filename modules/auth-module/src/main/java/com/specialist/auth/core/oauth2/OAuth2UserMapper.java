package com.specialist.auth.core.oauth2;

import com.specialist.auth.domain.auth_provider.Provider;
import com.specialist.auth.exceptions.NullOAuth2UserAttributesException;
import com.specialist.auth.exceptions.NullOAuth2UserException;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Map;
import java.util.UUID;

public final class OAuth2UserMapper {

    public OAuthUserEntity toEntity(Provider provider, UUID accountId, OAuth2User oAuth2User) {
        if (oAuth2User == null) {
            throw new NullOAuth2UserException();
        } else if (oAuth2User.getAttributes() == null) {
            throw new NullOAuth2UserAttributesException();
        }
        Map<String, Object> attributes = oAuth2User.getAttributes();
        OAuthUserEntity entity = new OAuthUserEntity(accountId, (String) attributes.get("email"));
        switch (provider) {
            case GOOGLE -> {
                entity.setFirstName((String) attributes.get("given_name"));
                entity.setLastName((String) attributes.get("family_name"));
                entity.setAvatarUrl((String) attributes.get("picture"));
            }
            case FACEBOOK -> {
                entity.setFirstName((String) attributes.get("first_name"));
                entity.setLastName((String) attributes.get("last_name"));
                Map<String, Object> pictureData = oAuth2User.getAttribute("picture");
                if (pictureData != null) {
                    Map<String, Object> data = (Map<String, Object>) pictureData.get("data");
                    if (data != null) {
                        entity.setAvatarUrl((String) data.get("url"));
                    }
                }
            }
            default -> {}
        }
        return entity;
    }
}
