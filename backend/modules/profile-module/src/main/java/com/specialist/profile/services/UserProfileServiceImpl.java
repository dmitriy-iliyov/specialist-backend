package com.specialist.profile.services;

import com.specialist.contracts.profile.ProfileType;
import com.specialist.profile.exceptions.UserNotFoundByIdException;
import com.specialist.profile.mappers.UserProfileMapper;
import com.specialist.profile.models.ProfileFilter;
import com.specialist.profile.models.UserProfileEntity;
import com.specialist.profile.models.dtos.PrivateUserResponseDto;
import com.specialist.profile.models.dtos.PublicUserResponseDto;
import com.specialist.profile.models.dtos.UserCreateDto;
import com.specialist.profile.models.dtos.UserUpdateDto;
import com.specialist.profile.models.enums.ScopeType;
import com.specialist.profile.repositories.UserProfileRepository;
import com.specialist.utils.pagination.PageResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserProfileServiceImpl implements UserProfileService, ProfilePersistService<UserCreateDto, PrivateUserResponseDto>,
                                               ProfileReadStrategy, ProfileDeleteStrategy {

    private final UserProfileRepository repository;
    private final UserProfileMapper mapper;

    @Transactional
    @Override
    public PrivateUserResponseDto save(UserCreateDto dto) {
        return mapper.toPrivateDto(repository.save(mapper.toEntity(dto)));
    }

    @Override
    public ProfileType getType() {
        return ProfileType.USER;
    }

    @Transactional
    @Override
    public PrivateUserResponseDto update(UserUpdateDto dto) {
        UserProfileEntity userProfileEntity = repository.findById(dto.getId()).orElseThrow(UserNotFoundByIdException::new);
        mapper.updateEntityFromDto(dto, userProfileEntity);
        return mapper.toPrivateDto(repository.save(userProfileEntity));
    }

    @Transactional
    @Override
    public void updateAvatarUrlById(UUID id, String avatarUrl) {
        repository.updateAvatarUrlById(id, avatarUrl);
    }

    @Transactional(readOnly = true)
    @Override
    public PrivateUserResponseDto findPrivateById(UUID id) {
        return mapper.toPrivateDto(repository.findById(id).orElseThrow(UserNotFoundByIdException::new));
    }

    @Transactional(readOnly = true)
    @Override
    public PublicUserResponseDto findPublicById(UUID id) {
        return mapper.toPublicDto(repository.findById(id).orElseThrow(UserNotFoundByIdException::new));
    }

    @Transactional(readOnly = true)
    @Override
    public PageResponse<?> findAll(ScopeType scope, ProfileFilter filter) {
        Pageable pageable;
        if (filter.isAsc() != null && filter.isAsc()) {
            pageable = org.springframework.data.domain.PageRequest.of(
                    filter.getPageNumber(), filter.getPageSize(), Sort.by("creatorRating").ascending()
            );
        } else {
            pageable = org.springframework.data.domain.PageRequest.of(
                    filter.getPageNumber(), filter.getPageSize(), Sort.by("creatorRating").descending()
            );
        }
        Page<UserProfileEntity> entityPage = repository.findAll(pageable);
        if (scope.equals(ScopeType.PUBLIC)) {
            return new PageResponse<>(
                    mapper.toPublicDtoList(entityPage.getContent()),
                    entityPage.getTotalPages()
            );
        }
        return new PageResponse<>(
                mapper.toPrivateDtoList(entityPage.getContent()),
                entityPage.getTotalPages()
        );
    }

    @Transactional
    @Override
    public void deleteAllByIds(List<UUID> ids) {
        repository.deleteAllByIdIn(ids);
    }
}
