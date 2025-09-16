package com.specialist.profile.services;

import com.specialist.contracts.profile.ProfileType;
import com.specialist.profile.models.dtos.BasePrivateResponseDto;
import com.specialist.profile.models.dtos.UserCreateDto;

public interface ProfilePersistService<C extends UserCreateDto, P extends BasePrivateResponseDto> {
    P save(C dto);
    ProfileType getType();
}
