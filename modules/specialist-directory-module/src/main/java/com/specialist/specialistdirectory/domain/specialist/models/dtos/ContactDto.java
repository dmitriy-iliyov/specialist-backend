package com.specialist.specialistdirectory.domain.specialist.models.dtos;

import com.specialist.specialistdirectory.domain.specialist.models.enums.ContactType;

public record ContactDto(
        ContactType type,
        String value
) { }
