package com.specialist.contracts.auth;

import java.util.UUID;

public interface PrincipalDetails {
    UUID getAccountId();
    UUID getId();
}
