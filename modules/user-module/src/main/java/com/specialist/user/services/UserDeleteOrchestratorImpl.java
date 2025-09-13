package com.specialist.user.services;

import com.specialist.contracts.user.UserDeleteOrchestrator;
import com.specialist.contracts.user.UserType;
import com.specialist.user.exceptions.NullUserDeleteServiceException;
import com.specialist.user.repositories.AvatarStorage;
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
public class UserDeleteOrchestratorImpl implements UserDeleteOrchestrator {

    private final Map<UserType, UserDeleteService> userDeleteServiceMap;
    private final AvatarStorage avatarStorage;

    public UserDeleteOrchestratorImpl(List<UserDeleteService> userDeleteServices, AvatarStorage avatarStorage) {
        this.userDeleteServiceMap = userDeleteServices.stream()
                .collect(Collectors.toMap(UserDeleteService::getType, Function.identity()));
        this.avatarStorage = avatarStorage;
    }

    @Transactional
    @Override
    public void delete(UUID id, UserType type) {
        UserDeleteService userDeleteService = userDeleteServiceMap.get(type);
        if (userDeleteService == null) {
            log.error("UserDeleteService for user type {} not found.", type);
            throw new NullUserDeleteServiceException();
        }
        userDeleteService.deleteById(id);
        avatarStorage.deleteByUserId(id);
    }
}
