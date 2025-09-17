package com.specialist.profile.services;

import com.specialist.contracts.profile.ProfileType;
import com.specialist.contracts.profile.dto.BaseResponseDto;
import com.specialist.core.exceptions.models.BaseNotFoundException;
import com.specialist.profile.exceptions.EmptyStrategyMapException;
import com.specialist.profile.exceptions.NullStrategyException;
import com.specialist.profile.models.ProfileFilter;
import com.specialist.profile.models.dtos.BasePrivateResponseDto;
import com.specialist.profile.models.enums.ScopeType;
import com.specialist.utils.pagination.PageResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ProfileReadOrchestratorImpl implements ProfileReadOrchestrator {

    private final Map<ProfileType, ProfileReadStrategy> strategyMap;

    public ProfileReadOrchestratorImpl(List<ProfileReadStrategy> strategies) {
        this.strategyMap = strategies.stream().
                collect(Collectors.toMap(ProfileReadStrategy::getType, Function.identity()));
    }

    @Override
    public BasePrivateResponseDto findPrivateById(UUID id) {
        BaseNotFoundException lastException = null;
        for (ProfileReadStrategy strategy: strategyMap.values()) {
            try {
                return strategy.findPrivateById(id);
            } catch (BaseNotFoundException e) {
                lastException = e;
            }
        }
        throw resolveLastException(lastException);
    }

    @Override
    public BasePrivateResponseDto findPrivateById(UUID id, ProfileType type) {
        return resolveStrategy(type).findPrivateById(id);
    }

    @Override
    public BaseResponseDto findPublicById(UUID id) {
        BaseNotFoundException lastException = null;
        for (ProfileReadStrategy strategy: strategyMap.values()) {
            try {
                return strategy.findPublicById(id);
            } catch (BaseNotFoundException e) {
                lastException = e;
            }
        }
        throw resolveLastException(lastException);
    }

    @Override
    public PageResponse<?> findAll(ScopeType scopeType, ProfileFilter filter) {
        return resolveStrategy(filter.type()).findAll(scopeType, filter);
    }

    private ProfileReadStrategy resolveStrategy(ProfileType type) {
        ProfileReadStrategy strategy = strategyMap.get(type);
        if (strategy == null) {
            log.error("ProfileReadStrategy not found for profile type {}", type);
            throw new NullStrategyException();
        }
        return strategy;
    }

    private RuntimeException resolveLastException(BaseNotFoundException lastException) {
        if (lastException == null) {
            log.error("ProfileReadStrategy map is empty.");
            return new EmptyStrategyMapException();
        }
        return lastException;
    }
}
