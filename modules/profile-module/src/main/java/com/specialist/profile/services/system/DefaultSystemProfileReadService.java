package com.specialist.profile.services.system;

import com.specialist.contracts.profile.SystemProfileReadService;
import com.specialist.contracts.profile.dto.UnifiedProfileResponseDto;
import com.specialist.profile.mappers.ProfileProjectionMapper;
import com.specialist.profile.models.ShortProfileProjection;
import com.specialist.profile.repositories.SpecialistProfileRepository;
import com.specialist.profile.repositories.UserProfileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DefaultSystemProfileReadService implements SystemProfileReadService {

    private final UserProfileRepository userProfileRepository;
    private final SpecialistProfileRepository specialistProfileRepository;
    private final ProfileProjectionMapper mapper;

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
}
