package com.aidcompass.message.confirmation.repositories;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.concurrent.TimeUnit;


@Repository
@RequiredArgsConstructor
public class RedisConfirmationRepository implements ConfirmationRepository {

    private final RedisTemplate<String, String> redisTemplate;


    @Override
    public void save(String key, String value, Long ttl) {
        redisTemplate.opsForValue().set(key, value, ttl, TimeUnit.SECONDS);
    }

    @Override
    public Optional<String> findAndDeleteByToken(String key) {
        return Optional.ofNullable(redisTemplate.opsForValue().getAndDelete(key));
    }
}
