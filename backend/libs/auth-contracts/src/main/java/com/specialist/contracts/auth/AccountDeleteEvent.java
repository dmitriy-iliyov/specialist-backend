package com.specialist.contracts.auth;

import java.util.UUID;

public record AccountDeleteEvent(
        UUID id,
        UUID accountId,
        String stringRole
) { }
