package com.specialist.contracts.auth;

import java.util.UUID;

public record AccountCreateEvent(
        UUID id,
        String email
) { }
