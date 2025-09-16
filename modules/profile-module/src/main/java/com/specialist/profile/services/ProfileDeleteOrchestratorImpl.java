package com.specialist.profile.services;

import com.specialist.contracts.profile.ProfileDeleteOrchestrator;
import com.specialist.contracts.profile.ProfileType;
import com.specialist.profile.exceptions.NullProfileDeleteServiceException;
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
public class ProfileDeleteOrchestratorImpl implements ProfileDeleteOrchestrator {

    private final Map<ProfileType, ProfileDeleteService> serviceMap;
    private final AvatarStorage avatarStorage;

    public ProfileDeleteOrchestratorImpl(List<ProfileDeleteService> profileDeleteServices, AvatarStorage avatarStorage) {
        this.serviceMap = profileDeleteServices.stream()
                .collect(Collectors.toMap(ProfileDeleteService::getType, Function.identity()));
        this.avatarStorage = avatarStorage;
    }

    @Transactional
    @Override
    public void delete(UUID id, ProfileType type) {
        ProfileDeleteService profileDeleteService = serviceMap.get(type);
        if (profileDeleteService == null) {
            log.error("ProfileDeleteService for user type {} not found.", type);
            throw new NullProfileDeleteServiceException();
        }
        profileDeleteService.deleteById(id);
        avatarStorage.deleteByUserId(id);
    }
}
