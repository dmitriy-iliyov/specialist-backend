package com.specialist.profile.services.system;

import com.specialist.contracts.profile.SystemProfilePersistService;
import com.specialist.contracts.profile.dto.ShortProfileCreateDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
@RequiredArgsConstructor
public final class RedisSystemProfilePersistService implements SystemProfilePersistService {

    private Long TTL = 300L;
    private final RedisTemplate<String, String> redisTemplate;

    @Override
    public void save(ShortProfileCreateDto dto) {
        redisTemplate.opsForValue().set(
                "profiles:emails:%s".formatted(dto.id()),
                dto.email(),
                Duration.ofSeconds(TTL)
        );
    }
}
