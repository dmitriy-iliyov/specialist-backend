package com.aidcompass.message.models;

import java.util.UUID;

public record EmailConfirmationEvent(
     UUID id,
     UUID accountId,
     String email
) { }