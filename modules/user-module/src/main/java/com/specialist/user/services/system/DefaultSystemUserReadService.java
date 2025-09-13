package com.specialist.user.services.system;

import com.specialist.contracts.user.SystemUserReadService;
import com.specialist.contracts.user.dto.PublicUserResponseDto;
import com.specialist.user.mappers.UserProjectionMapper;
import com.specialist.user.repositories.UserRepository;
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
public class DefaultSystemUserReadService implements SystemUserReadService {

    private final UserRepository repository;
    private final UserProjectionMapper mapper;

    @Transactional(readOnly = true)
    @Override
    public Map<UUID, PublicUserResponseDto> findAllByIdIn(Set<UUID> ids) {
        if (ids.isEmpty()) {
            return Collections.emptyMap();
        }
        return mapper.toPublicDtoList(repository.findAllByIdIn(ids)).stream()
                .collect(Collectors.toMap(PublicUserResponseDto::getId, Function.identity()));
    }
}
