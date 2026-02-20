package com.specialist.contracts.auth;

import java.util.UUID;

public record ImmediatelyAccountDeleteEvent(
        UUID id,
        UUID accountId,
        String stringRole
) { }
