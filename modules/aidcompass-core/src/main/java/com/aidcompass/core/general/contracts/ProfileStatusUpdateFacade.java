package com.aidcompass.core.general.contracts;


import com.aidcompass.core.general.contracts.enums.ServiceType;

import java.util.UUID;

public interface ProfileStatusUpdateFacade {
    void updateProfileStatus(ServiceType type, UUID userId);
}