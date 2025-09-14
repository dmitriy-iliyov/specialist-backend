package com.specialist.contracts.user;

import com.specialist.contracts.user.dto.UnifiedProfileResponseDto;

import java.util.Map;
import java.util.Set;
import java.util.UUID;

public interface SystemProfileReadService {
    Map<UUID, UnifiedProfileResponseDto> findAllByIdIn(Set<UUID> ids);
}
