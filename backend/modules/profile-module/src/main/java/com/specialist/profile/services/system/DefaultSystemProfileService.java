package com.specialist.profile.services.system;

import com.specialist.contracts.notification.SystemShortProfileResponseDto;
import com.specialist.contracts.notification.SystemShortProfileService;
import com.specialist.contracts.profile.SystemProfilePersistService;
import com.specialist.contracts.profile.SystemProfileService;
import com.specialist.contracts.profile.dto.ShortProfileCreateDto;
import com.specialist.contracts.profile.dto.UnifiedProfileResponseDto;
import com.specialist.profile.mappers.ProfileProjectionMapper;
import com.specialist.profile.models.ShortProfileProjection;
import com.specialist.profile.repositories.SpecialistProfileRepository;
import com.specialist.profile.repositories.UserProfileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DefaultSystemProfileService implements SystemProfilePersistService, SystemProfileService, SystemShortProfileService {

    private Long TTL = 300L;
    private final RedisTemplate<String, String> redisTemplate;
    private final UserProfileRepository userProfileRepository;
    private final SpecialistProfileRepository specialistProfileRepository;
    private final ProfileProjectionMapper mapper;

    @Override
    public void save(ShortProfileCreateDto dto) {
        redisTemplate.opsForValue().set(
                "profiles:emails:%s".formatted(dto.id()),
                dto.email(),
                Duration.ofSeconds(TTL)
        );
    }

    @Transactional(readOnly = true)
    @Override
    public Map<UUID, UnifiedProfileResponseDto> findAllByIdIn(Set<UUID> ids) {
        if (ids.isEmpty()) {
            return Collections.emptyMap();
        }
        List<ShortProfileProjection> userProjections = userProfileRepository.findAllByIdIn(ids);
        userProjections.addAll(specialistProfileRepository.findAllByIdIn(ids));
        return userProjections.stream()
                .map(mapper::toPublicDto)
                .collect(Collectors.toMap(UnifiedProfileResponseDto::getId, Function.identity()));
    }

    @Transactional(readOnly = true)
    @Override
    public Map<UUID, SystemShortProfileResponseDto> findAllShortByIdIn(Set<UUID> ids) {
        if (ids.isEmpty()) {
            return Collections.emptyMap();
        }
        List<ShortProfileProjection> userProjections = userProfileRepository.findAllByIdIn(ids);
        userProjections.addAll(specialistProfileRepository.findAllByIdIn(ids));
        return userProjections.stream()
                .map(mapper::toSystemShortDto)
                .collect(Collectors.toMap(SystemShortProfileResponseDto::id, Function.identity()));
    }
}
