package com.specialist.profile.services;

import com.specialist.contracts.auth.AccountDeleteEvent;
import com.specialist.contracts.profile.ProfileType;
import com.specialist.profile.exceptions.NullStrategyException;
import com.specialist.profile.repositories.AvatarStorage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
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
    public void delete(List<AccountDeleteEvent> events) {
        Map<ProfileType, List<UUID>> ids = new HashMap<>();
        for (AccountDeleteEvent event : events) {
            ProfileType type = ProfileType.fromStringRole(event.stringRole());
            ids.computeIfAbsent(type, k -> new ArrayList<>())
                    .add(event.accountId());
        }
        List<UUID> summaryIds = new ArrayList<>();
        ids.keySet().forEach(
                k -> {
                    ProfileDeleteStrategy strategy = strategyMap.get(k);
                    if (strategy == null) {
                        log.error("ProfileDeleteService for user type {} not found.", k);
                        throw new NullStrategyException();
                    }
                    List<UUID> currentIds = ids.get(k);
                    strategy.deleteAllByIds(currentIds);
                    summaryIds.addAll(currentIds);
                }
        );
        avatarStorage.deleteAllByUserIds(summaryIds);
    }
}
