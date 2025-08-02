package com.aidcompass.message.repositories;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class RedisConfirmationRepository implements ConfirmationRepository {

    private final RedisTemplate<String, String> redisTemplate;

    @Override
    public void save(String code, UUID accountId) {
        redisTemplate.o
    }

    public void getAndDelete(String code) {

    }
}
