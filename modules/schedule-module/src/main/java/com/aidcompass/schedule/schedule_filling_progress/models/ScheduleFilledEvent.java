package com.aidcompass.schedule.schedule_filling_progress.models;

import com.aidcompass.core.security.domain.authority.models.Authority;

import java.util.UUID;

public record ScheduleFilledEvent(
        UUID userId,
        Authority authority
) { }
