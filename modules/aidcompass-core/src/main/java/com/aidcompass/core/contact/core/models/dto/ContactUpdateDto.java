package com.aidcompass.core.contact.core.models.dto;

import com.aidcompass.contracts.ContactType;
import com.aidcompass.core.contact.core.models.markers.UpdateDto;
import com.aidcompass.core.contact.core.validation.annotation.Contact;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

@Contact
public record ContactUpdateDto(
        @NotNull(message = "Contact id shouldn't be null!")
        @Positive(message = "Contact id should be positive!")
        Long id,

        @NotNull(message = "Contact type shouldn't be null!")
        ContactType type,

        @NotBlank(message = "Contact shouldn't be empty or blank!")
        String contact,

        @JsonProperty("is_primary")
        @NotNull(message = "Primary flag shouldn't be empty or blank!")
        boolean isPrimary
) implements UpdateDto { }
