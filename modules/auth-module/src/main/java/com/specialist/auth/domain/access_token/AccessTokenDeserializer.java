package com.specialist.auth.domain.access_token;

import com.specialist.auth.domain.access_token.models.AccessToken;

public interface AccessTokenDeserializer {
    AccessToken deserialize(String rawToken);
}
