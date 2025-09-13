package com.specialist.user.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.specialist.user.models.dtos.BasePrivateResponseDto;
import com.specialist.user.models.dtos.CreateRequest;

public interface UserPersistOrchestrator {
    BasePrivateResponseDto save(CreateRequest request) throws JsonProcessingException;
}
