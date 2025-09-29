package com.specialist.profile.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.specialist.contracts.profile.ProfileType;
import com.specialist.profile.models.dtos.BasePrivateResponseDto;
import com.specialist.profile.models.dtos.UpdateRequest;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

public interface ProfileManagementOrchestrator {
    BasePrivateResponseDto update(UpdateRequest request) throws JsonProcessingException;
    String updateAvatar(UUID id, ProfileType type, MultipartFile avatar);
}
