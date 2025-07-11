package com.aidcompass.users.detail;

import com.aidcompass.users.detail.mapper.DetailMapper;
import com.aidcompass.users.detail.models.DetailDto;
import com.aidcompass.users.detail.models.DetailEntity;
import com.aidcompass.users.detail.models.PrivateDetailResponseDto;
import com.aidcompass.core.general.contracts.ProfileStatusUpdateFacade;
import com.aidcompass.core.general.contracts.enums.ServiceType;
import com.aidcompass.users.general.exceptions.UserAlreadyExistException;
import com.aidcompass.users.general.exceptions.detail.DetailEntityNotFoundByUserIdException;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UnifiedDetailService implements DetailService, PersistEmptyDetailService {

    private final DetailRepository repository;
    private final DetailMapper mapper;
    private final ProfileStatusUpdateFacade profileStatusUpdateFacade;
    private static final String USER_CACHE_NAME_TEMPLATE = "%s:public:full";
    private final CacheManager cache;


    @Transactional
    @Override
    public DetailEntity saveEmpty(UUID userId) {
        if (repository.existsByUserId(userId)) {
            throw new UserAlreadyExistException();
        }
        DetailEntity entity = new DetailEntity(userId);
        entity = repository.save(entity);
        return entity;
    }

    @Transactional
    @Override
    public PrivateDetailResponseDto update(UUID userId, DetailDto dto, ServiceType serviceType) {
        DetailEntity entity = repository.findByUserId(userId).orElseThrow(DetailEntityNotFoundByUserIdException::new);
        mapper.updateEntityFromDto(dto, entity);
        entity = repository.save(entity);

        String cacheName = USER_CACHE_NAME_TEMPLATE.formatted(serviceType.getCacheName());
        Objects.requireNonNull(cache.getCache(cacheName)).evict(userId);

        if (entity.getAboutMyself() != null && !entity.getAboutMyself().trim().isEmpty()) {
            profileStatusUpdateFacade.updateProfileStatus(serviceType, userId);
        }

        return mapper.toPrivateDetailDto(entity);
    }
}
