package com.specialist.user.services.system;

import com.specialist.contracts.user.SystemUserReadService;
import com.specialist.contracts.user.dto.PublicUserResponseDto;
import com.specialist.user.mappers.UserProjectionMapper;
import com.specialist.user.models.ShortUserProjection;
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
public class CompositeSystemUserReadService implements SystemUserReadService {

    private final UserRepository userRepository;
    private final SpecialistRepository specialistRepository;
    private final UserProjectionMapper mapper;

    @Transactional(readOnly = true)
    @Override
    public Map<UUID, PublicUserResponseDto> findAllByIdIn(Set<UUID> ids) {
        if (ids.isEmpty()) {
            return Collections.emptyMap();
        }
        List<ShortUserProjection> userProjections = userRepository.findAllByIdIn(ids);
        userProjections.addAll(specialistRepository.findAllByIdIn(ids));
        return userProjections.stream()
                .map(mapper::toPublicDto)
                .collect(Collectors.toMap(PublicUserResponseDto::getId, Function.identity()));
    }
}
