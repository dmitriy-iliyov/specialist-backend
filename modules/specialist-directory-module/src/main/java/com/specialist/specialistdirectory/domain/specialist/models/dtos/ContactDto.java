package com.specialist.specialistdirectory.domain.specialist.models.dtos;

import com.specialist.contracts.specialistdirectory.dto.ContactType;

public record ContactDto(
        ContactType type,
        String value
) { }
