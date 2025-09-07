package com.specialist.user.services;

import com.specialist.contracts.user.PublicUserResponseDto;
import com.specialist.contracts.user.ShortUserCreateDto;
import com.specialist.contracts.user.SystemUserService;
import com.specialist.user.mappers.UserMapper;
import com.specialist.user.models.UserEntity;
import com.specialist.user.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SystemUserServiceImpl implements SystemUserService {

    private final UserRepository repository;
    private final UserMapper mapper;

    @Transactional
    @Override
    public void save(ShortUserCreateDto dto) {
        repository.save(mapper.toEntity(dto));
    }

    @Transactional
    @Override
    public void updateEmailById(UUID id, String email) {
        repository.updateEmailById(id, email);
    }

    @Transactional(readOnly = true)
    @Override
    public Map<UUID, PublicUserResponseDto> findAllByIdIn(Set<UUID> ids) {
        List<UserEntity> entityList = repository.findAllByIdIn(ids);
        return mapper.toPublicDtoList(entityList).stream()
                .collect(Collectors.toMap(PublicUserResponseDto::getId, Function.identity()));
    }
}
