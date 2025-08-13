package com.specialist.user.infrastructure.rest;

import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Repository
public class InMemoryBearerTokenRepository implements BearerTokenRepository {

    private final Map<UUID, String> storage = new HashMap<>();

    @Override
    public String save(UUID key, String value) {
        return storage.put(key, value);
    }

    @Override
    public String getByKey(UUID key) {
        return storage.get(key);
    }
}
