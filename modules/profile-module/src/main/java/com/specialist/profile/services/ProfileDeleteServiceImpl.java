package com.specialist.profile.services;

import com.specialist.contracts.profile.ProfileType;
import com.specialist.profile.exceptions.NullStrategyException;
import com.specialist.profile.repositories.AvatarStorage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ProfileDeleteServiceImpl implements ProfileDeleteService {

    private final Map<ProfileType, ProfileDeleteStrategy> strategyMap;
    private final AvatarStorage avatarStorage;

    public ProfileDeleteServiceImpl(List<ProfileDeleteStrategy> profileDeleteStrategies, AvatarStorage avatarStorage) {
        this.strategyMap = profileDeleteStrategies.stream()
                .collect(Collectors.toMap(ProfileDeleteStrategy::getType, Function.identity()));
        this.avatarStorage = avatarStorage;
    }

    @Transactional
    @Override
    public void delete(UUID id, ProfileType type) {
        ProfileDeleteStrategy strategy = strategyMap.get(type);
        if (strategy == null) {
            log.error("ProfileDeleteService for user type {} not found.", type);
            throw new NullStrategyException();
        }
        strategy.deleteById(id);
        avatarStorage.deleteByUserId(id);
    }
}
