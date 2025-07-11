package com.aidcompass.users.profile_status;


import com.aidcompass.core.general.contracts.enums.ServiceType;

import java.util.UUID;

public interface ProfileStatusUpdateService {

    ServiceType getType();

    int updateProfileStatus(UUID userId);
}
