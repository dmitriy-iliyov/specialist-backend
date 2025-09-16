package com.specialist.contracts.profile;

import com.specialist.contracts.profile.dto.UnifiedProfileResponseDto;

import java.util.Map;
import java.util.Set;
import java.util.UUID;

public interface SystemProfileAggregator {
    Map<UUID, UnifiedProfileResponseDto> findAllByIdIn(Set<UUID> ids);
}
