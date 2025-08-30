package com.specialist.contracts.auth;

import java.util.UUID;

public interface AccountAuthorityFacade {
    void postUserCreateDemand(UUID accountId);
}
