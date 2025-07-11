package com.aidcompass.core.contact.core.models.dto;


import com.aidcompass.contracts.ContactType;
import com.fasterxml.jackson.annotation.JsonProperty;

public record PrivateContactResponseDto(
        Long id,

        ContactType type,

        String contact,

        @JsonProperty("is_primary")
        boolean isPrimary,

        @JsonProperty("is_confirmed")
        boolean isConfirmed

        //
) { }
