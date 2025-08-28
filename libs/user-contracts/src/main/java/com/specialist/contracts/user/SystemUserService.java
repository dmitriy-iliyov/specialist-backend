package com.specialist.contracts.user;

import java.util.Map;
import java.util.Set;
import java.util.UUID;

public interface SystemUserService {
    void updateEmailById(UUID id, String email);

    Map<UUID, PublicUserResponseDto> findAllByIdIn(Set<UUID> ids);
}
