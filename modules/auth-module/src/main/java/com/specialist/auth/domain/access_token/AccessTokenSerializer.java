package com.specialist.auth.domain.access_token;

import com.specialist.auth.domain.access_token.models.AccessToken;

public interface AccessTokenSerializer {
    String serialize(AccessToken accessToken);
}
