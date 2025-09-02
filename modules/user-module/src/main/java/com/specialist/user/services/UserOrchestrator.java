package com.specialist.user.services;

import com.specialist.user.models.dtos.PrivateUserResponseDto;
import com.specialist.user.models.dtos.UserUpdateDto;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

public interface UserOrchestrator {
    PrivateUserResponseDto update(UserUpdateDto dto);

    String updateAvatar(MultipartFile avatar, UUID id);
}
