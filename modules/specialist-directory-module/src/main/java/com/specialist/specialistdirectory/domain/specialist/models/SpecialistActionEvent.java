package com.specialist.specialistdirectory.domain.specialist.models;

import com.specialist.specialistdirectory.domain.specialist.models.enums.ActionType;
import com.specialist.specialistdirectory.domain.specialist.models.enums.ContactType;

public record SpecialistActionEvent(
        ActionType type,
        String contact,
        ContactType contactType,
        String code
) { }