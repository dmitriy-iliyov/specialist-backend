package com.specialist.user.services.system;

import com.specialist.contracts.user.SystemProfileReadService;
import com.specialist.contracts.user.dto.UnifiedProfileResponseDto;
import com.specialist.user.mappers.ProfileProjectionMapper;
import com.specialist.user.models.ShortProfileProjection;
import com.specialist.user.repositories.SpecialistRepository;
import com.specialist.user.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DefaultSystemProfileReadService implements SystemProfileReadService {

    private final UserRepository userRepository;
    private final SpecialistRepository specialistRepository;
    private final ProfileProjectionMapper mapper;

    @Transactional(readOnly = true)
    @Override
    public Map<UUID, UnifiedProfileResponseDto> findAllByIdIn(Set<UUID> ids) {
        if (ids.isEmpty()) {
            return Collections.emptyMap();
        }
        List<ShortProfileProjection> userProjections = userRepository.findAllByIdIn(ids);
        userProjections.addAll(specialistRepository.findAllByIdIn(ids));
        return userProjections.stream()
                .map(mapper::toPublicDto)
                .collect(Collectors.toMap(UnifiedProfileResponseDto::getId, Function.identity()));
    }
}
