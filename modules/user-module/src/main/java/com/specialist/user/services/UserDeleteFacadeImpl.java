package com.specialist.user.services;

import com.specialist.contracts.user.UserDeleteFacade;
import com.specialist.user.repositories.AvatarStorage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserDeleteFacadeImpl implements UserDeleteFacade {

    private final UserService userService;
    private final AvatarStorage avatarStorage;

    @Transactional
    @Override
    public void delete(UUID id) {
        userService.deleteById(id);
        avatarStorage.deleteByUserId(id);
    }
}
