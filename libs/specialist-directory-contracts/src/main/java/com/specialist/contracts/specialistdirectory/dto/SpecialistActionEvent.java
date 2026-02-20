package com.specialist.contracts.specialistdirectory.dto;

public record SpecialistActionEvent(
        ActionType type,
        String contact,
        ContactType contactType,
        String code
) { }