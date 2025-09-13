package com.specialist.user.services.system;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public final class RedisUserEmailService implements UserEmailService {

    private final RedisTemplate<String, String> redisTemplate;

    @Override
    public String findById(UUID id) {
        return redisTemplate.opsForValue().getAndDelete("users:emails:%s".formatted(id));
    }
}
