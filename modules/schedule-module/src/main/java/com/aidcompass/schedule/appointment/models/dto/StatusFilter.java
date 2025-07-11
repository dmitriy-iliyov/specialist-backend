package com.aidcompass.schedule.appointment.models.dto;

public record StatusFilter(
    boolean scheduled,
    boolean canceled,
    boolean completed
) { }
