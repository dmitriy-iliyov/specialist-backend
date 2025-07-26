package com.aidcompass.contracts.user;

import java.util.Map;
import java.util.Set;
import java.util.UUID;

public interface SystemUserService {
    Map<UUID, PublicUserResponseDto> findAllByIdIn(Set<UUID> ids);
}
