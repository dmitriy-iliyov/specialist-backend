package com.specialist.user.services.system;

import com.specialist.contracts.user.SystemUserPersistService;
import com.specialist.contracts.user.dto.ShortUserCreateDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
@RequiredArgsConstructor
public final class RedisSystemUserPersistService implements SystemUserPersistService {

    private Long TTL = 300L;
    private final RedisTemplate<String, String> redisTemplate;

    @Override
    public void save(ShortUserCreateDto dto) {
        redisTemplate.opsForValue().set(
                "users:emails:%s".formatted(dto.id()),
                dto.email(),
                Duration.ofSeconds(TTL)
        );
    }
}
