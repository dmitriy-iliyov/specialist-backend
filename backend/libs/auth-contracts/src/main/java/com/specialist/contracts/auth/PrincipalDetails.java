package com.specialist.contracts.auth;

import java.util.UUID;

public interface PrincipalDetails {
    UUID getId();
    UUID getAccountId();
    String getStringRole();
}
