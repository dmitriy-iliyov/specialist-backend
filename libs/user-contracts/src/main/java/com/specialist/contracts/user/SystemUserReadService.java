package com.specialist.contracts.user;

import com.specialist.contracts.user.dto.BaseResponseDto;
import com.specialist.contracts.user.dto.PublicUserResponseDto;

import java.util.Map;
import java.util.Set;
import java.util.UUID;

public interface SystemUserQueryService {
    Map<UUID, PublicUserResponseDto> findAllByIdIn(Set<UUID> ids);
}
