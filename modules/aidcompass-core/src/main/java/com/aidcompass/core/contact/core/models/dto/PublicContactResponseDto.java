package com.aidcompass.core.contact.core.models.dto;


import com.aidcompass.contracts.ContactType;

public record PublicContactResponseDto(
        ContactType type,
        String contact,
        boolean isPrimary
) { }
