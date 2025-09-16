package com.specialist.profile.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.specialist.profile.models.dtos.BasePrivateResponseDto;
import com.specialist.profile.models.dtos.CreateRequest;

public interface ProfilePersistOrchestrator {
    BasePrivateResponseDto save(CreateRequest request) throws JsonProcessingException;
}
