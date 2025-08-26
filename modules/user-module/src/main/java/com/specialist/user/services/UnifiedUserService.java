package com.specialist.user.services;

import com.specialist.contracts.user.CreatorRatingUpdateEvent;
import com.specialist.contracts.user.PublicUserResponseDto;
import com.specialist.contracts.user.SystemUserService;
import com.specialist.user.exceptions.UserNotFoundByIdException;
import com.specialist.user.mappers.UserMapper;
import com.specialist.user.models.UserEntity;
import com.specialist.user.models.dtos.BaseUserDto;
import com.specialist.user.models.dtos.PrivateUserResponseDto;
import com.specialist.user.models.dtos.UserUpdateDto;
import com.specialist.user.models.enums.ScopeType;
import com.specialist.user.repositories.UserRepository;
import com.specialist.utils.pagination.PageRequest;
import com.specialist.utils.pagination.PageResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service("unifiedUserService")
@RequiredArgsConstructor
public class UnifiedUserService implements UserService, SystemUserService, CreatorRatingService {

    private final UserRepository repository;
    private final UserMapper mapper;


    @Transactional
    @Override
    public PrivateUserResponseDto save(BaseUserDto dto) {
        return mapper.toPrivateDto(repository.save(mapper.toEntity(dto)));
    }

    @Transactional
    @Override
    public PrivateUserResponseDto update(UserUpdateDto dto, EmailChangeHandler handler) {
        UserEntity userEntity = repository.findById(dto.getId()).orElseThrow(UserNotFoundByIdException::new);
        if (!userEntity.getEmail().equals(dto.getEmail())) {
            handler.handle(userEntity.getId(), dto.getEmail());
        }
        mapper.updateEntityFromDto(dto, userEntity);
        return mapper.toPrivateDto(repository.save(userEntity));
    }

    @Transactional
    @Override
    public void updateAvatarUrlById(UUID id, String avatarUrl) {
        repository.updateAvatarUrlById(id, avatarUrl);
    }

    @Transactional
    @Override
    public void updateById(CreatorRatingUpdateEvent dto) {
        UserEntity entity = repository.findById(dto.creatorId()).orElseThrow(UserNotFoundByIdException::new);

        long summarySpecialistRating = entity.getSummarySpecialistRating() + dto.earnedRating();
        long specialistReviewCount = entity.getSpecialistReviewCount() + dto.reviewCount();
        double creatorRating = (double) summarySpecialistRating / specialistReviewCount;

        entity.setSummarySpecialistRating(summarySpecialistRating);
        entity.setSpecialistReviewCount(specialistReviewCount);
        entity.setCreatorRating(creatorRating);

        repository.save(entity);
    }

    @Transactional(readOnly = true)
    @Override
    public boolean existsById(UUID id) {
        return false;
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
    public PageResponse<?> findAll(ScopeType scope, PageRequest page) {
        Pageable pageable;
        if (page.asc() != null && page.asc()) {
            pageable = org.springframework.data.domain.PageRequest.of(
                    page.pageNumber(), page.pageSize(), Sort.by("creatorRating").ascending()
            );
        } else {
            pageable = org.springframework.data.domain.PageRequest.of(
                    page.pageNumber(), page.pageSize(), Sort.by("creatorRating").descending()
            );
        }
        Page<UserEntity> entityPage = repository.findAll(pageable);
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

    @Transactional(readOnly = true)
    @Override
    public Map<UUID, PublicUserResponseDto> findAllByIdIn(Set<UUID> ids) {
        List<UserEntity> entityList = repository.findAllByIdIn(ids);
        return mapper.toPublicDtoList(entityList).stream()
                .collect(Collectors.toMap(PublicUserResponseDto::getId, Function.identity()));
    }

    @Transactional
    @Override
    public void deleteById(UUID id) {
        repository.deleteById(id);
    }
}
