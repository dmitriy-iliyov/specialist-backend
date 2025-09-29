package com.specialist.profile.services.system;

import com.specialist.contracts.profile.SystemProfileService;
import com.specialist.contracts.profile.dto.UnifiedProfileResponseDto;
import com.specialist.profile.mappers.ProfileProjectionMapper;
import com.specialist.profile.repositories.UserProfileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SystemUserProfileService implements SystemProfileService {

    private final UserProfileRepository repository;
    private final ProfileProjectionMapper mapper;

    @Transactional(readOnly = true)
    @Override
    public Map<UUID, UnifiedProfileResponseDto> findAllByIdIn(Set<UUID> ids) {
        if (ids.isEmpty()) {
            return Collections.emptyMap();
        }
        return repository.findAllByIdIn(ids).stream()
                .map(mapper::toPublicDto)
                .collect(Collectors.toMap(UnifiedProfileResponseDto::getId, Function.identity()));    }
}
