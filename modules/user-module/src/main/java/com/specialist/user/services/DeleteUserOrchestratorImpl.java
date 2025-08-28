package com.specialist.user.services;

import com.specialist.user.repositories.AvatarStorage;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DeleteUserOrchestratorImpl implements DeleteUserOrchestrator {

    private final UserService userService;
    private final AvatarStorage avatarStorage;

    @Transactional
    @Override
    public void delete(UUID id, UUID refreshTokenId, HttpServletResponse response) {
        userService.deleteById(id);
        avatarStorage.deleteByUserId(id);
    }
}
