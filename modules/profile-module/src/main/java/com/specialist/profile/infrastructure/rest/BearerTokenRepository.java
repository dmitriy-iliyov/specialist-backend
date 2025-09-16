package com.specialist.profile.infrastructure.rest;

import java.util.UUID;

public interface BearerTokenRepository {
    String save(UUID key, String value);

    String getByKey(UUID key);
}
