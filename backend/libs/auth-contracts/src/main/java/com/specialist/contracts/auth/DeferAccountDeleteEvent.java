package com.specialist.contracts.auth;

import java.util.UUID;

public record DeferAccountDeleteEvent(
        UUID accountId,
        String stringRole
) { }
